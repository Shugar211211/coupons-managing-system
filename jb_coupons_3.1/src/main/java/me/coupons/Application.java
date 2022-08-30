package me.coupons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

//import me.coupons.console.Test;
import me.coupons.daily_job.ExpiredCouponsDailyCleanerImpl;

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
		
//		try 
//		{
//			// Check if daily job is running
//			while(ctx.getBean(ExpiredCouponsDailyCleanerImpl.class).processingDailyJob())
//				Thread.sleep(10L);
//		} 
//		catch (InterruptedException e) {e.printStackTrace();}
//		// stop app
//		((ConfigurableApplicationContext) ctx).close();
	}
}
