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
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
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

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final ItemProcessor rentProcessor;
    private final RentRepository rentRepository;
    private final TenantProfileRepository tenantProfileRepository;
    @PersistenceContext
    private EntityManager entityManager;


    @Bean
    public FlatFileItemReader<Rent> itemReader(){

//        System.out.println(new File("src/main/resources/rents.csv").getAbsolutePath());
        Resource resource = new ClassPathResource("rents.csv");
        System.out.println("File exists: " + resource.exists());
        System.out.println("File path: " + resource.getDescription());

        FlatFileItemReader<Rent> itemReader = new FlatFileItemReader<>();
//        itemReader.setResource(new FileSystemResource("src/main/resources/rents.csv"));
        itemReader.setResource(new ClassPathResource("rents.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);//skip reading the header
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
                        newProfile.setId(fieldSet.readLong("tenant_profile_id"));
                        newProfile.setKeycloakUserId("keycloak-"+fieldSet.readString("tenant_profile_id"));
                        var savedprofile = tenantProfileRepository.save(newProfile);
                        entityManager.flush();
                        return savedprofile;
                    });


            rent.setTenantProfile(profile);

            rent.setSuiteId(fieldSet.readLong("suite_id"));

            return rent;
        }
    }


    @Bean
    public RepositoryItemWriter<Rent> writer(){
        RepositoryItemWriter<Rent> writer = new RepositoryItemWriter<>();
        writer.setRepository(rentRepository);
        writer.setMethodName("save");//invoking the save method from the studentrepository
        return writer;
    }

    @Bean
    public Step importStep(){
        return new StepBuilder("csvImport",jobRepository)
                .<Rent,Rent>chunk(100,platformTransactionManager)
                .reader(itemReader())
                .processor(rentProcessor)
                .writer(writer())
                .taskExecutor(jobTaskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor jobTaskExecutor(){
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }

    @Bean
    public Job buildJob(){
        /*
         The JobBuilder requires a unique name for the job (in this case, "importRent") and a
         JobRepository, which is responsible for managing the execution state of jobs.
        * */
        return new JobBuilder("importRent", jobRepository)
                .start(importStep())

                .build();

    }

}
