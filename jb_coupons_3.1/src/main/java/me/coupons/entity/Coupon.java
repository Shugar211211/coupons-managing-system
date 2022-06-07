package me.coupons.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="coupons", schema = "jb_coupons_3")
@JsonIgnoreProperties({ "company_id" })
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "id")
public class Coupon {
	
	// fields
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, columnDefinition="INT UNSIGNED")
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY, 
				cascade= {CascadeType.PERSIST, CascadeType.MERGE, 
						 CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="company_id")
	@JsonIgnore
	private Company company;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "category_id")
	private Category category;
	
	@Column(name="title", unique=false, nullable=false, columnDefinition="VARCHAR(255)")
	private String title;
	
	@Column(name="description", unique=false, nullable=false, columnDefinition="VARCHAR(4096)")
	private String description;
	
	@Column(name="start_date", unique=false, nullable=false, columnDefinition="DATE")
	private Date startDate;
	
	@Column(name="end_date", unique=false, nullable=false, columnDefinition="DATE")
	private Date endDate;
	
	@Column(name="amount", unique=false, nullable=false, columnDefinition="INT")
	private int amount;
	
	@Column(name="price", unique=false, nullable=false, columnDefinition="DOUBLE")
	private double price;
	
	@Column(name="image", unique=false, nullable=true, columnDefinition="VARCHAR(1024)")
	private String image;
	
	@ManyToMany(fetch=FetchType.LAZY, 
				cascade= {CascadeType.DETACH,
					  CascadeType.MERGE,
					  CascadeType.PERSIST,
					  CascadeType.REFRESH})
	@JoinTable(name="customers_vs_coupons",
			   joinColumns=@JoinColumn(name="coupon_id"),
			   inverseJoinColumns=@JoinColumn(name="customer_id")
			   )
	@JsonIgnore
	private List<Customer> customers;
	
	// constructors
	
	public Coupon() {
		
	}
	public Coupon(int id, 
				  Company company, 
				  Category category, 
				  String title, 
				  String description, 
				  Date startDate, 
				  Date endDate,
				  int amount, 
				  double price, 
				  String image) {
		this.id = id;
		this.company = company;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}
	
	public Coupon( Company company, 
			  Category category, 
			  String title, 
			  String description, 
			  Date startDate, 
			  Date endDate,
			  int amount, 
			  double price, 
			  String image) {
	this.id=-1;
	this.company = company;
	this.category = category;
	this.title = title;
	this.description = description;
	this.startDate = startDate;
	this.endDate = endDate;
	this.amount = amount;
	this.price = price;
	this.image = image;
}
	
	// getters & setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategoryId(Category category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public List<Customer> getCustomers() {
		return customers;
	}
	
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	public void addCustomer(Customer customer) {
		if(customers == null)
			customers = new ArrayList<Customer>();
//		customers.setCoupon(this);
		customers.add(customer);
	}
	
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", companyId="
//	+ company
				+ ", categoryId=" + category + ", title=" + title
				+ ", description=" + description + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", amount="
				+ amount + ", price=" + price + ", image=" + image + "]";
	}
}
