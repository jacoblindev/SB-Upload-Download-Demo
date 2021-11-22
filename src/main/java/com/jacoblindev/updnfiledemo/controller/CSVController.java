package com.jacoblindev.updnfiledemo.controller;

import java.util.List;

import com.jacoblindev.updnfiledemo.helper.CSVHelper;
import com.jacoblindev.updnfiledemo.helper.ResponseMessage;
import com.jacoblindev.updnfiledemo.model.Tutorial;
import com.jacoblindev.updnfiledemo.service.CSVService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/v1/csv")
@CrossOrigin("http://localhost:8081")
public class CSVController {
    @Autowired
    CSVService csvService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadCSVFile(@RequestParam("file") MultipartFile file) {
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                csvService.save(file);
                return new ResponseEntity<>(
                        new ResponseMessage("Uploaded successfully: " + file.getOriginalFilename() + "！"),
                        HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(
                        new ResponseMessage("Couldn't upload the file: " + file.getOriginalFilename() + "！"),
                        HttpStatus.EXPECTATION_FAILED);
            }
        }
        return new ResponseEntity<>(new ResponseMessage("Please upload a CSV file！"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials() {
        try {
            List<Tutorial> tutorials = csvService.getAllTutorials();
            return tutorials.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
