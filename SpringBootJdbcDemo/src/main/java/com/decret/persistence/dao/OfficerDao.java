package com.decret.persistence.dao;

import java.util.List;
import java.util.Optional;

import com.decret.persistence.entety.Officer;

public interface OfficerDao {

	Officer save(Officer officer);

	Optional<Officer> findById(Integer id);

	List<Officer> findAll();

	long count();

	void delete(Officer officer);

}
