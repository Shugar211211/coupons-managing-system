package com.jb.coupons_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jb.coupons_project.test.Test;
import com.jb.coupons_project.utils.daily_job.ExpiredCouponsDailyCleanerImpl;

@SpringBootApplication
@EnableScheduling
public class Application 
{
	/**
	 * This method runs the application.
	 * @param args
	 */
	public static void main(String[] args) 
	{
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
			
		Test test = (Test) ctx.getBean(Test.class);
		
		test.testAll();
		
		try 
		{
			// Check if daily job is running
			while(ctx.getBean(ExpiredCouponsDailyCleanerImpl.class).processingDailyJob())
				Thread.sleep(10L);
		} 
		catch (InterruptedException e) {e.printStackTrace();}
		((ConfigurableApplicationContext) ctx).close();
	}
}
