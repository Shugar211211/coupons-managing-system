package com.jb.coupons_project.utils.daily_job;

import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jb.coupons_project.repository.CouponRepository;

@Component
@Scope("singleton")
public class ExpiredCouponsDailyCleanerImpl implements ExpiredCouponsDailyCleaner 
{
	@Autowired
	private CouponRepository couponRepository;
	
	private boolean executingDailyJobNow = false;	// daily job execution flag
	
	private String clientMsg="";
	
	/**
	 * Return true if daily job is running right now or false otherwise
	 * @return true - daily job running / false - daily job idle
	 */
	@Override
	public boolean processingDailyJob()
	{
		return executingDailyJobNow;
	}
	
	/**
	 * Return client message.
	 * @return client message
	 */
	@Override
	public String getClientMsg()
	{
		return this.clientMsg;
	}
	
	/**
	 * Run the task of cleaning expired coupons every 24 hours
	 */
	@Override
	@Scheduled(fixedRate = 1000L * 60L * 60L * 24L)
	@Transactional
	public void runDailyCleaner()
	{
		executingDailyJobNow = true; // setting flag to prevent terminating daily job 
		
		Date currentDate = new Date(System.currentTimeMillis());
		this.clientMsg = "Cleaning coupons by date "+currentDate;
		System.out.println("--------------------------------------------------");
		System.out.println("Start daily job");
		System.out.println(this.clientMsg);
		int deletedCouponsCount = couponRepository.deleteCouponsOlderThan(currentDate);
		this.clientMsg = "Done cleaning: "+deletedCouponsCount+" coupons deleted";
		System.out.println(this.clientMsg);
		System.out.println("Stop daily job");
		System.out.println("--------------------------------------------------");
		
		executingDailyJobNow = false; // setting flag to allow terminating daily job
	}
}
