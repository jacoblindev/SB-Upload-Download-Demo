package com.jacoblindev.updnfiledemo.controller;

import java.util.List;

import com.jacoblindev.updnfiledemo.helper.ExcelHelper;
import com.jacoblindev.updnfiledemo.helper.ResponseMessage;
import com.jacoblindev.updnfiledemo.model.Tutorial;
import com.jacoblindev.updnfiledemo.service.ExcelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:8081")
public class ExcelController {

    @Autowired
    ExcelService excelFileService;

    @PostMapping("/excel/upload")
    public ResponseEntity<ResponseMessage> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                excelFileService.save(file);
                message = "Uploaded the excel file successfully： " + file.getOriginalFilename();
                return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.EXPECTATION_FAILED);
            }
        }

        message = "Please upload an excel file!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials() {
        try {
            List<Tutorial> tutorials = excelFileService.getAllTutorials();
            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/excel/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "tutorials.xlsx";
        InputStreamResource file = new InputStreamResource(excelFileService.load());
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.set("Content-disposition", "attachment; filename=" + filename);
        resHeaders.set("Content-Type", "application/vnd.ms-excel");
        return new ResponseEntity<>(file, resHeaders, HttpStatus.OK);
    }

    @GetMapping("/excel/template")
    public ResponseEntity<Resource> getTemplate() {
        String filename = "tutorials.xlsx";
        InputStreamResource file = new InputStreamResource(excelFileService.loadTemplate());
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.set("Content-disposition", "attachment; filename=" + filename);
        resHeaders.set("Content-Type", "application/vnd.ms-excel");
        return new ResponseEntity<>(file, resHeaders, HttpStatus.OK);
    }
}
