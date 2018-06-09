package com.decret.persistence.dao;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.Matchers.contains;
import com.decret.persistence.entety.Officer;
import com.decret.persistence.entety.Rank;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class OfficerDaoImplTest {

	@Autowired
	OfficerDao dao;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testSave() {
		Officer officer = new Officer(Rank.LIEUTENANT, "Ajay", "Sharma");
		officer = dao.save(officer);
		assertNotNull(officer.getId());

	}

	@Test
	public void testFindAll() {
		 List<String> dbNames = dao.findAll().stream()
		            .map(Officer::getLastName)
		            .collect(Collectors.toList());
		 assertThat(dbNames, contains("Kirk", "Picard", "Sisko", "Janeway", "Archer"));
	
	}

	@Test
	public void testFindByIdThatExist() {

		Optional<Officer> officer = dao.findById(1);
		assertEquals(officer.isPresent(), true);

	}

	@Test
	public void testFindByIdThatDoNotExist() {

		Optional<Officer> officer = dao.findById(1000);
		assertFalse(officer.isPresent());

	}

	@Test
	public void testCount() {
		long count = dao.count();
		assertEquals(count, 5);
	}

	@Test
	public void testDelete() {

		IntStream.range(1, 6).forEach(id -> {
			Optional<Officer> officer = dao.findById(id);
			assertTrue(officer.isPresent());
			dao.delete(officer.get());
		});
		assertEquals(0, dao.count());

	}

}
