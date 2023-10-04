package com.example.demo.controller.api;

import com.example.demo.model.Information;
import com.example.demo.service.InformationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class InformationRestController {

  private final InformationService informationService;

  public InformationRestController(InformationService informationService) {
    this.informationService = informationService;
  }

  @GetMapping("/api/v1/information")
  public List<Information> productList() {
    return informationService.getAllInformations();
  }

}
