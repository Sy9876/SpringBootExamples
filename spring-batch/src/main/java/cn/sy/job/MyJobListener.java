package cn.sy.job;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class MyJobListener implements JobExecutionListener {
	
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("MyJobListener beforeJob");
	}
	
	public void afterJob(JobExecution jobExecution) {
		System.out.println("MyJobListener afterJob");
	}
}
