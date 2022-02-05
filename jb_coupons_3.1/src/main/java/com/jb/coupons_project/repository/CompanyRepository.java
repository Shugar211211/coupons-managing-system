package com.jb.coupons_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jb.coupons_project.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>  
{
	@Query("FROM Company c WHERE c.email= :email AND c.password= :password")
	Optional<Company> findByEmailAndPassword(@Param("email") String email, 
											 @Param("password") String password);
	
	@Query("FROM Company c WHERE c.name= :name")
	Optional<Company> findByName(@Param("name") String name);
	
	@Query("FROM Company c WHERE c.email= :email")
	Optional<Company> findByEmail(@Param("email") String email);
	
//	@Query("SELECT id, email, name, password FROM Company")
//	List<Company> findAll();
}
