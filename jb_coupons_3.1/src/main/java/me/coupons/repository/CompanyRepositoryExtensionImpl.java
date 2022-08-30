package me.coupons.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import me.coupons.entity.Company;

@Repository
@Transactional
public class CompanyRepositoryExtensionImpl implements CompanyRepositoryExtension {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public void updateCompanySkipPassword(Company company) {
		Query query = entityManager.createNativeQuery("UPDATE `companies` SET `email` = ?1, `name` = ?2 WHERE `id` = ?3 ");
		query.setParameter(1, company.getEmail());
		query.setParameter(2, company.getName());
		query.setParameter(4, company.getId());
		query.executeUpdate();
	}

//	@Override
//	public void resetCompanyPassword(int id, String password) {
//		Query query = entityManager.createNativeQuery("UPDATE `companies` SET `password` = ?1 WHERE `id` = ?2 ");
//		query.setParameter(1, password);
//		query.setParameter(2, id);
//		query.executeUpdate();
//	}
}
