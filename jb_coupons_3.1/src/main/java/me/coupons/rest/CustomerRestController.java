package me.coupons.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.coupons.auth.utils.JwtUtil;
import me.coupons.entity.Category;
import me.coupons.entity.Coupon;
import me.coupons.entity.Customer;
import me.coupons.rest.custom_exceptions.EntityRetrievalException;
import me.coupons.service.WebAwareCustomerService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/customer")
public class CustomerRestController {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private WebAwareCustomerService customerService;
	
	// endpoint for PUT /coupons - purchase coupon
	@PutMapping("/coupons")
	public Coupon deletePurchase(@RequestBody Coupon coupon,
								 @RequestHeader("Authorization") String authHeader) {
		final String jwt = authHeader.substring(7);
		Map<String, Object>claims = jwtUtil.extractAllClaims(jwt);
		int id = ((Integer)claims.get("id")).intValue();
		customerService.deletePurchase(id, coupon);
		if(!this.customerService.getClientMsg().equals("Coupon purchase deleted")) {
			throw new RuntimeException(this.customerService.getClientMsg());
		}
		return coupon;
	}
	
	// endpoint for GET /coupons/awailable - return list of coupons that are awailable for purchase
	@GetMapping("/coupons/awailable")
	public List<Coupon> getAwailableCoupons(@RequestHeader("Authorization") String authHeader) {
		final String jwt = authHeader.substring(7);
		Map<String, Object>claims = jwtUtil.extractAllClaims(jwt);
		int id = ((Integer)claims.get("id")).intValue();		
		List<Coupon> coupons = customerService.getAwailableCoupons(id);
		if(coupons == null) {
			throw new RuntimeException(this.customerService.getClientMsg());
		}
		return coupons;
	}
	
	// endpoint for GET/categories - get list of all categories
		@GetMapping("/categories")
		public String getCategories() {
			return this.customerService.getCategoriesList();
		}	
	
	// endpoint for GET "/coupons/{couponId}" - return coupon by coupon id
	@GetMapping("/coupons/{couponId}")
	public Coupon getCouponById(@PathVariable int couponId) {
	Coupon coupon = this.customerService.getCouponById(couponId);
		if(coupon == null) {
			throw new EntityRetrievalException(this.customerService.getClientMsg());
		}
		return coupon;
	}
		
	// endpoint for GET /coupons - return list of coupons bought by this customer
	@GetMapping("/coupons")
	public List<Coupon> getCustomerCoupons(@RequestHeader("Authorization") String authHeader) {
		final String jwt = authHeader.substring(7);
		Map<String, Object>claims = jwtUtil.extractAllClaims(jwt);
		int id = ((Integer)claims.get("id")).intValue();
		List<Coupon> coupons= this.customerService.getCustomerCoupons(id);
		if(coupons == null) {
			throw new RuntimeException(this.customerService.getClientMsg());
		}
		return coupons;
	}
	
	// endpoint for GET /coupons/category - return list of coupons in given category
	@GetMapping("/coupons/category")
	public List<Coupon> getCustomerCouponsByCategory(@RequestParam("category") Category category,
													 @RequestHeader("Authorization") String authHeader) {
		final String jwt = authHeader.substring(7);
		Map<String, Object>claims = jwtUtil.extractAllClaims(jwt);
		int id = ((Integer)claims.get("id")).intValue();
		List<Coupon> coupons= this.customerService.getCustomerCoupons(id, category);
		if(coupons == null) {
			throw new RuntimeException(this.customerService.getClientMsg());
		}
		return coupons;
	}
	
	// endpoint for GET /coupons/maxprice - return list of coupons under the given price
	@GetMapping("/coupons/maxprice")
	public List<Coupon> getCustomerCouponsByMaxPrice(@RequestParam("maxprice") double maxPrice,
													 @RequestHeader("Authorization") String authHeader) {
		final String jwt = authHeader.substring(7);
		Map<String, Object>claims = jwtUtil.extractAllClaims(jwt);
		int id = ((Integer)claims.get("id")).intValue();
		List<Coupon> coupons = customerService.getCustomerCoupons(id, maxPrice);
		if(coupons == null) {
			throw new RuntimeException(this.customerService.getClientMsg());
		}
		return coupons;
	}
	
	// endpoint for GET /details - return customer details
	@GetMapping("/details")
	public Customer getCustomerDetails(@RequestHeader("Authorization") String authHeader) {
		final String jwt = authHeader.substring(7);
		Map<String, Object>claims = jwtUtil.extractAllClaims(jwt);
		int id = ((Integer)claims.get("id")).intValue();
		Customer customer = this.customerService.getCustomerDetails(id);
		if(customer == null) {
			throw new EntityRetrievalException(this.customerService.getClientMsg());
		}
		return customer;
	}
	
	// endpoint for PUT /coupons/awailable - purchase coupon
	@PutMapping("/coupons/awailable")
	public Coupon purchaseCoupon(@RequestBody Coupon coupon,
								 @RequestHeader("Authorization") String authHeader) {
		final String jwt = authHeader.substring(7);
		Map<String, Object>claims = jwtUtil.extractAllClaims(jwt);
		int id = ((Integer)claims.get("id")).intValue();
		customerService.purchaseCoupon(id, coupon);
		if(!this.customerService.getClientMsg().equals("Coupon purchased")) {
			throw new RuntimeException(this.customerService.getClientMsg());
		}
		return coupon;
	}
}
