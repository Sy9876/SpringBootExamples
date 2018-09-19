package cn.sy.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.repository.support.SimpleJobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

//    @Bean
//    public JobRepository simpleJobRepository() {
//    	MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
//    	JobRepository jobRepository = null;
//    	try {
//    		jobRepository = factory.getJobRepository();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	System.out.println("BatchConfiguration JobRepository jobRepository=" + jobRepository);
//    	return jobRepository;
//    }
//    
//    @Bean
//    public SimpleJobLauncher simpleJobLauncher() {
//    	SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
//    	return simpleJobLauncher;
//    }
    
//    // tag::readerwriterprocessor[]
//    @Bean
//    public FlatFileItemReader<Person> reader() {
//        return new FlatFileItemReaderBuilder<Person>()
//            .name("personItemReader")
//            .resource(new ClassPathResource("sample-data.csv"))
//            .delimited()
//            .names(new String[]{"firstName", "lastName"})
//            .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
//                setTargetType(Person.class);
//            }})
//            .build();
//    }

//    @Bean
//    public PersonItemProcessor processor() {
//        return new PersonItemProcessor();
//    }

//    @Bean
//    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
//        return new JdbcBatchItemWriterBuilder<Person>()
//            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//            .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
//            .dataSource(dataSource)
//            .build();
//    }
//    // end::readerwriterprocessor[]

    
    @Bean
    public MyStepListener myStepListener() {
    	return new MyStepListener();
    }
    
    @Bean
    public MyJobListener myJobListener() {
    	return new MyJobListener();
    }
    
    @Bean(name="myTasklet")
    @StepScope
    public Tasklet myTasklet() {
    	return new MyTasklet();
    }
    // tag::jobstep[]
    @Bean
    public Job importUserJob(MyJobListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(Tasklet myTasklet) {
        return stepBuilderFactory.get("step1")
        	.tasklet(myTasklet)
            .build();
    }
    // end::jobstep[]
}
