package com.example.demo.repository;

import com.example.demo.model.Information;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InformationJdbcRepository implements InformationRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public InformationJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Information> findAll() {
    return jdbcTemplate.query("select * from information", informationRowMapper);
  }

  @Override
  public Information insert(Information information) {
    var update = jdbcTemplate.update("INSERT INTO information(orig_name, uuid, extension, saved_name, saved_path, load_path, latitude, longitude)" +
      " VALUES(:origName, :uuid, :extension, :savedName, :savedPath, :loadPath, :latitude, :longitude)", toParamMap(information));
    if (update != 1) {
      throw new RuntimeException("Noting was inserted");
    }

    return information;
  }
  
  @Override
  public Optional<Information> findById(String uuid) {
    try {
      return Optional.ofNullable(
              jdbcTemplate.queryForObject("SELECT * FROM information WHERE uuid = :uuid",
                      Collections.singletonMap("uuid", uuid), informationRowMapper)
      );
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.update("DELETE FROM information", Collections.emptyMap());
  }

  private static final RowMapper<Information> informationRowMapper = (resultSet, i) -> {
    var origName = resultSet.getString("orig_name");
    var uuid = resultSet.getString("uuid");
    var extension = resultSet.getString("extension");
    var savedName = resultSet.getString("saved_name");
    var savedPath = resultSet.getString("saved_path");
    var loadPath = resultSet.getString("load_path");
    var latitude = resultSet.getDouble("latitude");
    var longitude = resultSet.getDouble("longitude");

    return new Information(origName, uuid, extension, savedName, savedPath, loadPath, latitude, longitude);
  };

  private Map<String, Object> toParamMap(Information information) {
    var paramMap = new HashMap<String, Object>();
    paramMap.put("origName", information.getOrigName());
    paramMap.put("uuid", information.getUuid());
    paramMap.put("extension", information.getExtension());
    paramMap.put("savedName", information.getSavedName());
    paramMap.put("savedPath", information.getSavedPath());
    paramMap.put("loadPath", information.getLoadPath());
    paramMap.put("latitude", information.getLatitude());
    paramMap.put("longitude", information.getLongitude());
    return paramMap;
  }

}
