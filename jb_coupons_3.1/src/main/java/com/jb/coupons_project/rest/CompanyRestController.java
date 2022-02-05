package com.jb.coupons_project.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jb.coupons_project.auth.utils.JwtUtil;
import com.jb.coupons_project.entity.Category;
import com.jb.coupons_project.entity.Company;
import com.jb.coupons_project.entity.Coupon;
import com.jb.coupons_project.rest.custom_exceptions.EntityRetrievalException;
import com.jb.coupons_project.service.web_aware.WebAwareCompanyService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/company")
public class CompanyRestController {
	
	@Autowired
	private WebAwareCompanyService companyService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	// endpoint for POST /coupons - add new coupon
	@PostMapping("/coupons")
	public Coupon addCoupon(@RequestBody Coupon coupon, 
							@RequestHeader("Authorization") String authHeader) {
		coupon.setId(0);
		
		final String jwt = authHeader.substring(7);
		Map<String, Object>claims = jwtUtil.extractAllClaims(jwt);
		int id = ((Integer)claims.get("id")).intValue();
		
//		Optional<Company> optCompany = companyRepository.findById(id);
//		Company company = optCompany.get();
//		coupon.setCompany(company);
//		couponRepository.save(coupon);
		companyService.addCoupon(id, coupon);
		if(!companyService.getClientMsg().equals("New coupon added")) { 
			throw new RuntimeException(companyService.getClientMsg());
		}
		return coupon;
	}
	
	// endpoint for DELETE /coupons - delete coupon
	@DeleteMapping("/coupons/{couponId}")
	public String deleteCoupon(@PathVariable int couponId) {
		
		companyService.deleteCoupon(couponId);
		if(!companyService.getClientMsg().equals("Coupon deleted")) { 
			throw new RuntimeException(companyService.getClientMsg());
		}
		return "{\"message\": \"Coupon deleted\"}";
//		Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);
//		if(optionalCoupon.isPresent()) {
//			couponRepository.deleteById(couponId);
//			return "Coupon deleted";
//		}
//		else
//			throw new RuntimeException("Coupon was not found");
	}
	
	// endpoint for GET/categories - get list of all categories
	@GetMapping("/categories")
	public String getCategories() {
		return this.companyService.getCategoriesList();
	}	
	
	// endpoint for GET /coupons - return list of all coupons issued by this company
	@GetMapping("/coupons")
	public List<Coupon> getCompanyCoupons(@RequestHeader("Authorization") String authHeader) {
		
		final String jwt = authHeader.substring(7);
		Map<String, Object>claims = jwtUtil.extractAllClaims(jwt);
		int id = ((Integer)claims.get("id")).intValue();
//		Optional<Company> optCompany = companyRepository.findById(id);
//		Company company = optCompany.get();
		List<Coupon> coupons = companyService.getCompanyCoupons(id);
		if(coupons == null) {
			throw new RuntimeException(companyService.getClientMsg());
		}
		return coupons;
	}
	
	// endpoint for GET /coupons/category - return list of all coupons in given category
	@GetMapping("/coupons/category")
	public List<Coupon> getCompanyCouponsByCategory(@RequestParam("category") Category category,
													@RequestHeader("Authorization") String authHeader) {
		final String jwt = authHeader.substring(7);
		Map<String, Object>claims = jwtUtil.extractAllClaims(jwt);
		int id = ((Integer)claims.get("id")).intValue();
//		List<Coupon> coupons = couponRepository.findAllCouponsByCompanyAndByCategory(id, category.toString());
//		return coupons;
		List<Coupon> coupons = companyService.getCompanyCoupons(id, category);
		if(coupons == null) {
			throw new RuntimeException(companyService.getClientMsg());
		}
		return coupons;
	}
	
	// endpoint for GET /coupons/price - return list of all coupons under given price
	@GetMapping("/coupons/price")
	public List<Coupon> getCompanyCouponsByMaxPrice(@RequestParam("maxprice") double maxPrice,
													@RequestHeader("Authorization") String authHeader) {	
		final String jwt = authHeader.substring(7);
		Map<String, Object>claims = jwtUtil.extractAllClaims(jwt);
		int id = ((Integer)claims.get("id")).intValue();
//		List<Coupon> coupons = couponRepository.findAllCouponsByCompanyUnderMaxPrice(id, maxPrice);
//		return coupons;
		List<Coupon> coupons = companyService.getCompanyCoupons(id, maxPrice);
		if(coupons == null) {
			throw new RuntimeException(companyService.getClientMsg());
		}
		return coupons;
	}
	
	// endpoint for GET /details - return company details
	@GetMapping("/details")
	public Company getCompanyDetails(@RequestHeader("Authorization") String authHeader) {
		final String jwt = authHeader.substring(7);
		Map<String, Object>claims = jwtUtil.extractAllClaims(jwt);
		int id = ((Integer)claims.get("id")).intValue();
		Company company = companyService.getCompanyDetails(id);
		if(company == null) {
			throw new RuntimeException(companyService.getClientMsg());
		}
		return company;
	}
	
	// endpoint for GET "/coupons/{couponId}" - return coupon by coupon id
	@GetMapping("/coupons/{couponId}")
	public Coupon getCouponById(@PathVariable int couponId) {
		Coupon coupon = this.companyService.getCouponById(couponId);
		if(coupon == null) {
			throw new EntityRetrievalException(this.companyService.getClientMsg());
		}
		return coupon;
	}
	
	// endpoint for PUT /coupons - update existing coupon
	@PutMapping("/coupons")
	public Coupon updateCoupon(@RequestBody Coupon coupon) {
		this.companyService.updateCoupon(coupon);
		if(!this.companyService.getClientMsg().equals("Coupon updated")) {
			throw new EntityRetrievalException(this.companyService.getClientMsg());
		}
//		couponRepository.save(coupon);
		return coupon;
	}
}
