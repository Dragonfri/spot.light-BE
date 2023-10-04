package com.example.demo.service;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.example.demo.model.Information;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface InformationService {

  List<Information> getAllInformations();

  Information createInformation(MultipartFile files) throws IOException, JpegProcessingException;

  public Optional<Information> getInformationById(String uuid);
}
