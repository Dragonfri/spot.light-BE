package com.example.demo.repository;

import com.example.demo.model.Information;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InformationRepository {

  List<Information> findAll();

  Information insert(Information information);

  public Optional<Information> findById(String uuid);

  void deleteAll();

}
