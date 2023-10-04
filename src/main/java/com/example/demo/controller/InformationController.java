package com.example.demo.controller;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.example.demo.model.Information;
import com.example.demo.service.InformationService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class InformationController {

  private final InformationService informationService;

  public InformationController(InformationService informationService) {
    this.informationService = informationService;
  }

  @GetMapping("/information")
  public String InformationPage(Model model) {
    var information = informationService.getAllInformations();
    model.addAttribute("informations", information);
    return "information-list";
  }

  @GetMapping("/new-information")
  public String newInformationPage() {
    return "new-information";
  }

  @PostMapping("/information")
  public String newInformation(@RequestParam("imagePath") MultipartFile file) throws IOException, JpegProcessingException {
    informationService.createInformation(file);
    return "redirect:/information";
  }

}
