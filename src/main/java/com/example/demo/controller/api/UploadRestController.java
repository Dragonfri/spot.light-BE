package com.example.demo.controller.api;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.example.demo.model.Information;
import com.example.demo.repository.InformationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@RestController
public class UploadRestController {
  private final InformationRepository informationRepository;
  private final ServerProperties serverProperties;
  private final String fileDir;

  public UploadRestController(InformationRepository informationRepository, ServerProperties serverProperties, @Value("${spring.servlet.multipart.location}") String fileDir) {
    this.informationRepository = informationRepository;
    this.serverProperties = serverProperties;
    this.fileDir = fileDir;
  }

  @PostMapping("/api/v1/upload")
  public Information createInformation(@RequestParam("image") MultipartFile files) throws IOException, JpegProcessingException {
    String origName = files.getOriginalFilename();
    String uuid = UUID.randomUUID().toString();
    String extension = origName.substring(origName.lastIndexOf("."));
    String savedName = uuid + extension;
    String protocol = serverProperties.getSsl() != null ? "https" : "http";
    String savedPath = fileDir + savedName;
    String loadPath = "http://49.50.175.53:8080" + "/img/" + savedName;

    files.transferTo(new File(savedName));

    String pdsLat = "";
    String pdsLon = "";

    double latitude = 0;    //위도
    double longitude = 0;    //경도

    File file = new File(fileDir + savedName);
    Metadata metadata = JpegMetadataReader.readMetadata(file);
    ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
    Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
    GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);

    if(gpsDirectory.containsTag(GpsDirectory.TAG_LATITUDE) && gpsDirectory.containsTag(GpsDirectory.TAG_LONGITUDE)) {

      pdsLat = String.valueOf(gpsDirectory.getGeoLocation().getLatitude());
      pdsLon = String.valueOf(gpsDirectory.getGeoLocation().getLongitude());

      latitude = Double.parseDouble(pdsLat);    //위도
      longitude = Double.parseDouble(pdsLon);    //경도
    }

    var information = new Information(origName, uuid, extension, savedName, savedPath, loadPath, latitude, longitude);
    return informationRepository.insert(information);
  }
}
