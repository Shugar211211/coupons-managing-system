package me.coupons.repository;

import me.coupons.entity.Company;

public interface CompanyRepositoryExtension {

	/**
	 * Update company, password field is ignored
	 * @param company: Company object
	 */
	void updateCompanySkipPassword(Company company);
	
//	/**
//	 * Reset company password
//	 * @param id: company id
//	 * @param password: new password
//	 */
//	void resetCompanyPassword(int id, String password);
	
}
