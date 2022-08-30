package me.coupons.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import me.coupons.entity.Category;
import me.coupons.entity.Company;
import me.coupons.entity.Coupon;
import me.coupons.repository.CompanyRepository;
import me.coupons.repository.CouponRepository;
import me.coupons.utils.InputValidator;

@Service
@RequestScope
public class CompanyServiceJpaImpl implements CompanyService{
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	private String clientMsg="";
	
	@Autowired
	private InputValidator inputValidator;
	
	@Override
	public String getClientMsg() {
		return clientMsg;
	}

	@Override
	public void addCoupon(int id, Coupon coupon) {
		
		// check if any coupon fields have invalid data
		Coupon validCoupon = inputValidator.validateCoupon(coupon);
		if(validCoupon == null)
		{
			this.clientMsg = inputValidator.getClientMsg();
			return;
		}	
		Optional<Company> optCompany = companyRepository.findById(id);
		Company company = optCompany.get();
		coupon.setCompany(company);
		
		// check if this company has coupon with this name
		List<Coupon> coupons = 
				this.couponRepository.findAllCouponsByTitleAndCompany(coupon.getTitle(), id);
		if(coupons.size()>0) 
		{
			this.clientMsg = "Can not add coupon "+coupon.getTitle()
				+". Coupon with this name has already been issued by "+company.getName();
			return;
		}

		// force round coupon price to 2 decimal numbers
		double price = coupon.getPrice();
		price = (int)(price*100);
		price = Math.round(price);
		
		// save coupon
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
	public String getCategoriesList() {
		String categoriesStr = "{\"categories\": [\"";
		for(Category c : Category.values()) { 
//			categoriesStr = categoriesStr + c.getCategoryDescription().toString() + "\", \""; 
			categoriesStr = categoriesStr + c + "\", \"";
		}
		return categoriesStr.substring(0, categoriesStr.length()-3) + "]}";
	}
	
	@Override
	public List<Coupon> getCompanyCoupons(int id) {
		
		Optional<Company> optCompany = companyRepository.findById(id);
		Company company = optCompany.get();
		if(company == null) {
			this.clientMsg = "Company not found";
			return null;
		}
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
	public List<Coupon> getCompanyCoupons(int id, Category category) {
		
		if(category == null)
		{
			this.clientMsg="Invalid category";
			return null;
		}
		
		List<Coupon> coupons = 
				couponRepository.findAllCouponsByCompanyAndByCategory(id, category.toString());
		if(coupons == null)
			this.clientMsg="Error retrieving coupons";
		if(coupons.size() == 0)
			this.clientMsg="No coupons found in this category for the company";
		this.clientMsg=coupons.size() + " coupons found in this category for the company";
		return coupons;
	}
	
	@Override
	public List<Coupon> getCompanyCoupons(int id, double maxPrice) {
		Optional<Company> optCompany = companyRepository.findById(id);
		Company company = optCompany.get();
		if(company == null) {
			this.clientMsg = "Company not found";
			return null;
		}
		List<Coupon> coupons = couponRepository.findAllCouponsByCompanyUnderMaxPrice(id, maxPrice);
		if(coupons == null)
			this.clientMsg="Error retrieving coupons";
		if(coupons.size() == 0)
			this.clientMsg="No coupons under this price found for the company";
		this.clientMsg=coupons.size() + " coupons under this price found for the company";
		return coupons;
	}

	@Override
	public Company getCompanyDetails(int id) {
		Optional<Company> optionalCompany = companyRepository.findById(id);//.getOne(companyID);
		Company company = optionalCompany.get();
		if(company == null)
			this.clientMsg="Company was not found";
		return company;
	}
	
	@Override
	public Coupon getCouponById(int id) {
		Optional<Coupon> optionalCoupon = couponRepository.findById(id);
		if( ! optionalCoupon.isPresent() ) {
			this.clientMsg = "Coupon not found";
		}
		return optionalCoupon.get();
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
		coupon.setCompany(originalCoupon.getCompany());
		
		if( originalCoupon.getCompany().getId() != coupon.getCompany().getId() )
		{
			this.clientMsg="Cannot update coupon: cannot update company id";
			return;
		}
		double price = coupon.getPrice();
		price = (int)(price*100);
		price = Math.round(price);
		coupon.setPrice((double)price/100);
//		coupon.setPrice(Math.round(coupon.getPrice()*100)/100);
		couponRepository.save(coupon);
		this.clientMsg="Coupon updated";
	}
}
