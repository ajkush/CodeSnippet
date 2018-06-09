package com.decret.persistence.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.decret.persistence.entety.Officer;

@Repository
public class OfficerDaoImpl implements OfficerDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Officer save(Officer officer) {
	    entityManager.persist(officer);
	    return officer;
	}

	@Override
	public Optional<Officer> findById(Integer id) {
	    return Optional.ofNullable(entityManager.find(Officer.class, id));
	}

	@Override
	public List<Officer> findAll() {
	    return entityManager.createQuery("select o from Officer o", Officer.class)
	                        .getResultList();
	}

	@Override
	public long count() {
	    return entityManager.createQuery("select count(o.id) from Officer o", Long.class)
	                        .getSingleResult();
	}

	@Override
	public void delete(Officer officer) {
	    entityManager.remove(officer);
	}

	
}
