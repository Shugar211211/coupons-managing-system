package com.jb.coupons_project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jb.coupons_project.entity.Category;
import com.jb.coupons_project.entity.Company;
import com.jb.coupons_project.entity.Coupon;
import com.jb.coupons_project.repository.CompanyRepository;
import com.jb.coupons_project.repository.CouponRepository;
import com.jb.coupons_project.repository.CustomerRepository;
import com.jb.coupons_project.utils.InputValidator;

@Service
@Scope("prototype")
public class CompanyServiceJpaImpl extends ClientFacade implements CompanyService {
	
	private InputValidator inputValidator;
	
	// Operation status message - used for client feedback
	private String clientMsg;
	
	private Integer companyID = null;
	
	public CompanyServiceJpaImpl() {
		this.clientMsg="";
	}

	@Autowired
	public CompanyServiceJpaImpl(CompanyRepository companyRepository, 
								 CustomerRepository customerRepository,
								 CouponRepository couponRepository,
								 InputValidator inputValidator) {
		super(companyRepository, customerRepository, couponRepository);
		this.clientMsg="";
		this.inputValidator = inputValidator;
	}
	
	@Override
	public String getClientMsg() {
		return clientMsg;
	}

	@Override
	public boolean login(String email, String password)
	{
		Optional<Company> optionalCompany = companyRepository.findByEmailAndPassword(email, password);
		
		if(optionalCompany.isPresent())
		{
			Company thisCompany = optionalCompany.get();
			this.clientMsg = thisCompany.getName() + ": logged in as company.";
			this.companyID = thisCompany.getId();
			return true;
		}
		else
		{
			this.clientMsg = "user " + email + " not found.";
			return false;
		}
	}

	@Override
	public void addCoupon(Coupon coupon) {
		
		// check if any coupon fields have invalid data
		Coupon validCoupon = inputValidator.validateCoupon(coupon);
		if(validCoupon == null)
		{
			this.clientMsg = inputValidator.getClientMsg();
			return;
		}		
		
		couponRepository.save(coupon);
		this.clientMsg="New coupon added";
	}

	@Override
	public void deleteCoupon(int couponID) {
		Optional<Coupon> optionalCoupon = couponRepository.findById(couponID);
		if(optionalCoupon.isPresent())
		{
			couponRepository.deleteById(couponID);
			this.clientMsg="Coupon deleted";
		}
		else
			this.clientMsg="Coupon was not found";
	}

	@Override
	public List<Coupon> getCompanyCoupons() {
		
		Optional<Company> optCompany = companyRepository.findById(this.companyID.intValue());
		Company company = optCompany.get();
		
//		List<Coupon> coupons = couponRepository.findCouponsByCompany(this.companyID.intValue());
		List<Coupon> coupons = company.getCoupons();
		
		if(coupons == null)
			this.clientMsg="Error retrieving coupons";
		if(coupons.size() == 0)
			this.clientMsg="No coupons found for the company";
		this.clientMsg=coupons.size() + " coupons found for the company";
		return coupons;
	}

	@Override
	public List<Coupon> getCompanyCoupons(double maxPrice) {
		
		List<Coupon> coupons = couponRepository.findAllCouponsByCompanyUnderMaxPrice(this.companyID.intValue(), maxPrice);
		if(coupons == null)
			this.clientMsg="Error retrieving coupons";
		if(coupons.size() == 0)
			this.clientMsg="No coupons under this price found for the company";
		this.clientMsg=coupons.size() + " coupons under this price found for the company";
		return coupons;
	}

	@Override
	public List<Coupon> getCompanyCoupons(Category category) {
		
		if(category == null)
		{
			this.clientMsg="Invalid category";
			return null;
		}
		
		List<Coupon> coupons = couponRepository.findAllCouponsByCompanyAndByCategory(this.companyID.intValue(), category.toString());
		if(coupons == null)
			this.clientMsg="Error retrieving coupons";
		if(coupons.size() == 0)
			this.clientMsg="No coupons found in this category for the company";
		this.clientMsg=coupons.size() + " coupons found in this category for the company";
		return coupons;
	}

	@Override
	public Company getCompanyDetails() {
		if(this.companyID == null)
		{
			this.clientMsg="Error: client not logged in as company";
			return null;
		}
		
		Optional<Company> optionalCompany = companyRepository.findById(companyID);//.getOne(companyID);
		Company company = optionalCompany.get();
		if(company == null)
			this.clientMsg="Company was not found";
		return company;
	}
	
	@Override
	public void updateCoupon(Coupon coupon) {
		
		// check if any coupon fields have invalid data
		if(coupon.getId() < 1)
		{
			this.clientMsg="Coupon id is not valid";
			return;
		}
		
		Coupon validCoupon = inputValidator.validateCoupon(coupon);
		if(validCoupon == null)
		{
			this.clientMsg = inputValidator.getClientMsg();
			return;
		}
		
		// protect from changing company_id field
		Optional<Coupon> optionalCoupon = couponRepository.findById(coupon.getId());
			Coupon originalCoupon = optionalCoupon.get();
		if( originalCoupon.getCompany().getId() != coupon.getCompany().getId() )
		{
			this.clientMsg="Cannot update coupon: cannot update company company id";
			return;
		}
		
		couponRepository.save(coupon);
		this.clientMsg="Coupon updated";
	}
}
