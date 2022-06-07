package me.coupons.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

@Entity
@Table(name="companies"
//		,
//		uniqueConstraints = {
//								@UniqueConstraint(columnNames = "name"),
//								@UniqueConstraint(columnNames = "email")
//        					}
		)

@JsonIgnoreProperties({ "coupons" })
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "id")
public class Company {
	
	// fields
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name", unique=true)
	private String name;
	
	@Column(name="email", unique=true)
	@NotNull
	private String email;
	
	@Column(name="password")
	@NotNull
	private String password;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="company", cascade= {CascadeType.ALL})
	private List<Coupon> coupons;
	
	// constructors
	
	public Company() {
		
	}

	public Company(String email, String password) {
		this.name = null;
		this.email = email;
		this.password = password;
	}
	
	public Company(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public Company(String name, String email, String password, List<Coupon> coupons) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}
	
	// getters & setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	public void add(Coupon coupon) {
		if(coupons == null)
			coupons = new ArrayList<Coupon>();
		coupon.setCompany(this);
		coupons.add(coupon);
	}
	
	public boolean hasCoupon(Coupon coupon) {
		if(coupons == null)
			return false;
		
		return coupons.contains(coupon);
	}
	
	public Coupon removeCoupon(Coupon coupon) {
		if(coupons == null)
			return null;
		
		if( ! coupons.contains(coupon) )
			return null;
		
		if( ! coupons.remove(coupon))
			return null;
		return coupon;
	}
	
	// toString
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}
}
