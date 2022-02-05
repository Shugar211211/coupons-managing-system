package com.jb.coupons_project.utils.daily_job;

public interface ExpiredCouponsDailyCleaner {

	/**
	 * Return true if daily job is running right now or false otherwise
	 * @return true - daily job running / false - daily job idle
	 */
	boolean processingDailyJob();

	/**
	 * Return client message.
	 * @return client message
	 */
	String getClientMsg();

	/**
	 * Run the task of cleaning expired coupons every 24 hours
	 */
	void runDailyCleaner();

}