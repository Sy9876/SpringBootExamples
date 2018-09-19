package cn.sy.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class MyStepListener implements StepExecutionListener {


	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("MyStepListener beforeStep");
		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("MyStepListener afterStep");
		return stepExecution.getExitStatus();
	}
}
