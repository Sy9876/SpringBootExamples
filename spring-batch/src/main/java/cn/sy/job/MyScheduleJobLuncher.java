package cn.sy.job;

import java.lang.reflect.Field;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
@EnableScheduling
public class MyScheduleJobLuncher {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job importUserJob;
	
	@Autowired
	@Qualifier("myTasklet")
	private Tasklet myTasklet;
	
	@Scheduled(cron="0/5 * * * * *")
	public void exec() {
		System.out.println("MyScheduleJobLuncher exec start");
		
//		RunIdIncrementer runIdIncrementer = new RunIdIncrementer();
//		runIdIncrementer.setKey("");
//		JobParameters jobParameters = runIdIncrementer.getNext(new JobParameters());

		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addDate("date", new Date());
		JobParameters jobParameters = builder.toJobParameters();
		
		try {
			Field field = null;
			Object target = null;
			Object value = null;
			
			field = ReflectionUtils.findField(MyTasklet.class, "myService");
			target = myTasklet;
			value = null;
			System.out.println("MyScheduleJobLuncher exec ReflectionUtils.setField:");
			System.out.println("  ReflectionUtils field:" + field);
//			System.out.println("  ReflectionUtils target:" + target);
			System.out.println("  ReflectionUtils value:" + value);
//			ReflectionUtils.setField(field, target, value);
			
			jobLauncher.run(importUserJob, jobParameters);
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
