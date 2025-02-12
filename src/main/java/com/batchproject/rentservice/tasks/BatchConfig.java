package com.batchproject.rentservice.tasks;

import com.batchproject.rentservice.models.rent.Rent;
import com.batchproject.rentservice.models.rent.RentDTO;
import com.batchproject.rentservice.models.rent.RentRepository;
import com.batchproject.rentservice.models.rent.RentStatus;
import com.batchproject.rentservice.models.tenant.TenantProfile;
import com.batchproject.rentservice.models.tenant.TenantProfileRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.net.BindException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private static final int CHUNK_SIZE = 400;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final ItemProcessor rentProcessor;
    private final RentRepository rentRepository;
    private final TenantProfileRepository tenantProfileRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final int CORE_POOL_AND_GRID_SIZE=8;


    @Bean
    @StepScope
    public FlatFileItemReader<Rent> itemReader(@Value("#{stepExecutionContext[minValue]}") int minValue,
                                               @Value("#{stepExecutionContext[maxValue]}") int maxValue){

//        System.out.println(new File("src/main/resources/rents.csv").getAbsolutePath());
        Resource resource = new ClassPathResource("rents.csv");
        System.out.println("File exists: " + resource.exists());
        System.out.println("File path: " + resource.getDescription());

        FlatFileItemReader<Rent> itemReader = new FlatFileItemReader<>();
//        itemReader.setResource(new FileSystemResource("src/main/resources/rents.csv"));
        itemReader.setResource(new ClassPathResource("rents.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(minValue);//skip reading the header/ rows that are read already, the header row is excluded already in partitioner when we started from row 1
        itemReader.setMaxItemCount(maxValue-minValue+1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Rent> lineMapper() {
        DefaultLineMapper<Rent> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(","); // for CSV, it's comma-delimited
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("amount", "due_date", "paid_date", "status", "tenant_profile_id", "suite_id");


        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(new CustomFieldSetMapper());
        return lineMapper;
    }

    public class CustomFieldSetMapper implements FieldSetMapper<Rent> {

        @Override
        public Rent mapFieldSet(FieldSet fieldSet)  {
            Rent rent = new Rent();

            rent.setAmount(fieldSet.readDouble("amount"));
            rent.setDueDate(LocalDate.parse(fieldSet.readString("due_date")));

            // Handle optional paidDate
            String paidDateStr = fieldSet.readString("paid_date");
            if (paidDateStr != null && !paidDateStr.isEmpty()) {
                rent.setPaidDate(LocalDate.parse(paidDateStr));
            }

            rent.setStatus(RentStatus.valueOf(fieldSet.readString("status")));

            // Create TenantProfile with ID
            TenantProfile profile = tenantProfileRepository.findById(fieldSet.readLong("tenant_profile_id"))
                    .orElseGet(()->{
                        TenantProfile newProfile = new TenantProfile();
//                        newProfile.setId(fieldSet.readLong("tenant_profile_id"));
                        newProfile.setKeycloakUserId("keycloak-"+fieldSet.readString("tenant_profile_id"));
                        return tenantProfileRepository.save(newProfile);
//                        var savedprofile = tenantProfileRepository.save(newProfile);
//                        entityManager.flush();
//                        return savedprofile;
                    });


            rent.setTenantProfile(profile);

            rent.setSuiteId(fieldSet.readLong("suite_id"));

            return rent;
        }
    }


//    @Bean
//    public RepositoryItemWriter<Rent> writer(){
//        RepositoryItemWriter<Rent> writer = new RepositoryItemWriter<>();
//        writer.setRepository(rentRepository);
//        writer.setMethodName("save");//invoking the save method from the studentrepository
//        return writer;
//    }
    @Bean
    @StepScope
    public ItemWriter<Rent> customerRentWriter() {
        return new ItemWriter<Rent>() {
            @Override
            public void write(Chunk<? extends Rent> chunk) throws Exception {

                if (!chunk.isEmpty()) {
                    // Get the first and last customer IDs
                    Rent firstRent = (Rent) chunk.getItems().get(0);
                    Rent secondRent =(Rent) chunk.getItems().get(chunk.getItems().size() - 1);

//                    System.out.println("Thread name: "+Thread.currentThread().getName()+" Writing chunk: First Rent amt = " + firstRent.getAmount() +
//                            ", Last Rent amt = " + secondRent.getAmount());
                }


                rentRepository.saveAll(chunk.getItems());//list of 5000/8 items here
            }
        };
    }

    @Bean
    public Step slaveStep(){
        return new StepBuilder("csvImport",jobRepository)
                .<Rent,Rent>chunk(CHUNK_SIZE,platformTransactionManager)
                .reader(itemReader(0,0))
                .processor(rentProcessor)
                .writer(customerRentWriter())
                .build();
    }

    @Bean
    public Step masterStep(){
        return new StepBuilder("masterStep",jobRepository)

                .partitioner(slaveStep().getName(),partitioner())
                .partitionHandler(partitionHandler())
                .build();
    }

    @Bean
    public PartitionHandler partitionHandler(){
        TaskExecutorPartitionHandler taskExecutorPartitionHandler = new TaskExecutorPartitionHandler();
        taskExecutorPartitionHandler.setGridSize(CORE_POOL_AND_GRID_SIZE);
        taskExecutorPartitionHandler.setTaskExecutor(batchTaskExecutor());

        taskExecutorPartitionHandler.setStep(slaveStep());
        return taskExecutorPartitionHandler;
    }

    @Bean
    public ColumnRangePartitioner partitioner(){
        return new ColumnRangePartitioner();
    }

//    @Bean
//    public TaskExecutor jobTaskExecutor(){
//        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
//        asyncTaskExecutor.setConcurrencyLimit(8);
//        return asyncTaskExecutor;
//    }
    @Bean
    public TaskExecutor batchTaskExecutor(){

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(16);
        taskExecutor.setCorePoolSize(CORE_POOL_AND_GRID_SIZE);
        taskExecutor.setQueueCapacity(20);
        return taskExecutor;
    }


    @Bean
    public Job buildJob(){
        /*
         The JobBuilder requires a unique name for the job (in this case, "importRent") and a
         JobRepository, which is responsible for managing the execution state of jobs.
        * */
        return new JobBuilder("importRent", jobRepository)
                .start(masterStep())

                .build();

    }

}
