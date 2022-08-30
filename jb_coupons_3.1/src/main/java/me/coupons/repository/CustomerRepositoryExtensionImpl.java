package me.coupons.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import me.coupons.entity.Customer;

@Repository
@Transactional
public class CustomerRepositoryExtensionImpl implements CustomerRepositoryExtension {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public void updateCustomerSkipPassword(Customer customer) {
		Query query = entityManager.createNativeQuery("UPDATE `customers` SET `email` = ?1, `first_name` = ?2, `last_name` = ?3 WHERE `id` = ?4 ");
		query.setParameter(1, customer.getEmail());
		query.setParameter(2, customer.getFirstName());
		query.setParameter(3, customer.getLastName());
		query.setParameter(4, customer.getId());
		query.executeUpdate();
	}

//	@Override
//	public void resetCustomerPassword(int id, String password) {
//		Query query = entityManager.createNativeQuery("UPDATE `customers` SET `password` = ?1 WHERE `id` = ?2 ");
//		query.setParameter(1, password);
//		query.setParameter(2, id);
//		query.executeUpdate();
//	}
}
