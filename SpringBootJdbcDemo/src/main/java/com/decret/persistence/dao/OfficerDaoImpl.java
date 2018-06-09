package com.decret.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.h2.expression.Rownum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.decret.persistence.entety.Officer;
import com.decret.persistence.entety.Rank;

@Repository
public class OfficerDaoImpl implements OfficerDao {

	JdbcTemplate jdbcTemplate;
	SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public OfficerDaoImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("officers").usingGeneratedKeyColumns("id");

	}

	@Override
	public Officer save(Officer officer) {

		Map<String, Object> parameters = new HashMap<>();
		// parameters.put("id", officer.getId());
		parameters.put("rank", officer.getRank());
		parameters.put("first_name", officer.getFirstName());
		parameters.put("last_name", officer.getLastName());
		KeyHolder id = simpleJdbcInsert.executeAndReturnKeyHolder(parameters);
		officer.setId((Integer) id.getKey());
		return officer;
	}

	@Override
	public Optional<Officer> findById(Integer id) {
		 if (!existsById(id)) return Optional.empty();
		return Optional.of(jdbcTemplate.queryForObject("select * from officers where id = ?", new RowMapper<Officer>() {

			@Override
			public Officer mapRow(ResultSet rs, int rowNum) throws SQLException {

				return new Officer(rs.getInt("id"), Rank.valueOf(rs.getString("rank")), rs.getString("first_name"),
						rs.getString("last_name"));
			}

		}, id));
	}

	private boolean existsById(Integer id) {
		return jdbcTemplate.queryForObject(
                "SELECT EXISTS(SELECT 1 FROM officers where id=?)", Boolean.class, id);
	}

	@Override
	public List<Officer> findAll() {
		return jdbcTemplate.query("SELECT * FROM officers", (rs, rowNum) -> new Officer(rs.getInt("id"),
				Rank.valueOf(rs.getString("rank")), rs.getString("first_name"), rs.getString("last_name")));

	}

	@Override
	public long count() {
		return jdbcTemplate.queryForObject("Select count(*) from officers", Long.class);
	}

	@Override
	public void delete(Officer officer) {
		jdbcTemplate.update("delete from officers where id=" + officer.getId());

	}

}
