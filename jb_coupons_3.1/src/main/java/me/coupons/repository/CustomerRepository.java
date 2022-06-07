package me.coupons.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import me.coupons.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>
{
	@Query("FROM Customer c WHERE c.email= :email AND c.password= :password")
	Optional<Customer> findByEmailAndPassword(@Param("email") String email, 
											  @Param("password") String password);
	
	@Query("FROM Customer c WHERE c.email= :email")
	Optional<Customer> findByEmail(@Param("email") String email);
}
