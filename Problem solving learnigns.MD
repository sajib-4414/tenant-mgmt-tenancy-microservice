Using Auditlog,
which is a robust system keeps track of when data is updated by which user.
its the spring way of having created by, updated by.
### how to?
have this in a base model
```declarative
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @CreatedDate
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(updatable = false, name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
```

Do an Implementation of auditor aware
```declarative

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
```

enable the jpa auditing and return the auditor awre bean
```declarative
@Configuration
@EnableJpaAuditing
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
```

Now extend any model with the base model, and spring will handle it.
```declarative
@Entity
public class Address extends BaseEntity{

    @Column(name = "apartment_no")
    private Integer apartmentNo;

    ....
}

```

## How to do error handling that happened in async in spring?
say u have async method like this,
```java
  @Async
    @Transactional
    public CompletableFuture<Address> createAddress(Address address) {
        address.setId(null);
        address.setStreetName(address.getStreetName().toLowerCase());
        address.setProvince(address.getProvince().toUpperCase());
        address.setPostCode(address.getPostCode().toUpperCase());
        List<String> addressParts = Arrays.stream(address.getPostCode().split(" ")).toList();
        if(addressParts.size()!=2 || addressParts.get(0).length()!=3 ||addressParts.get(1).length()!=3  )
            throw new RuntimeException("address invalid postcode");
        Address existingSameAddress = doesExist(address);
        if(existingSameAddress !=null)
            CompletableFuture.completedFuture(existingSameAddress);
        try{
            addressRepository.save(address);
            return CompletableFuture.completedFuture(address);
        }catch (Exception ex){
            System.out.println("exception happening here"+ex);
            throw new RuntimeException(ex);
        }

    }
```
You can still catch those errors with completable future like this
```java
 @PostMapping
    public CompletableFuture<ResponseEntity<Address>> createAddress(@Valid @RequestBody Address address) {
        CompletableFuture<ResponseEntity<Address>> future =
                addressService.createAddress(address)
                        .thenApply(result->{
                            return ResponseEntity.ok(result);
                        })
                        .exceptionally(ex->{
                            System.out.println("exception happened..."+ex);
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                        });
        return future;
    }
```
this way errors will not propagate to the global error handler, which is often
the better solution. In order for the error to propagate to Global error handler
controller advice, u can rethrow the error.
```java
 @PostMapping
    public CompletableFuture<ResponseEntity<Address>> createAddress(@Valid @RequestBody Address address) {
        CompletableFuture<ResponseEntity<Address>> future =
                addressService.createAddress(address)
                        .thenApply(result->{
                            return ResponseEntity.ok(result);
                        })
                        .exceptionally(ex->{
                            System.out.println("exception happened..."+ex);
                            //do a try catch here and throw a new RuntimeException(ex),
                            //although we are throwing runtime exception, but the exception
                            //type will be preserved, in the global error handler u will be able to
                            //catch specific error here
                        });
        return future;
    }
```
or even better, just omit that exceptionally part, if u want the error
to be handled by global error handler compeltely.
```java
 @PostMapping
    public CompletableFuture<ResponseEntity<Address>> createAddress(@Valid @RequestBody Address address) {
        CompletableFuture<ResponseEntity<Address>> future =
                addressService.createAddress(address)
                        .thenApply(result->{
                            return ResponseEntity.ok(result);
                        });
                        //if error it will be passed to global error handler automtically
        return future;
    }
```

**Problem: When saving an object, the nested object is not saved?** 

Solution: make sure your nested/child model has back reference to parent as `(one to many,
or many to one + mappedBy)` set, and the parent model has `cascadetype=all` or at least `save`.