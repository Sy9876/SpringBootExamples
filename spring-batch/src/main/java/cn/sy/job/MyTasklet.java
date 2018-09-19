package cn.sy.job;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import cn.sy.service.MyService;

public class MyTasklet implements Tasklet, InitializingBean {

	@Autowired
	private MyService myService;
	
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		System.out.println("MyTasklet execute start");
		myService.hello();
		System.out.println("MyTasklet execute end");
		
		return RepeatStatus.FINISHED;
	}
	
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(myService, "directory must be set");
	}
}
