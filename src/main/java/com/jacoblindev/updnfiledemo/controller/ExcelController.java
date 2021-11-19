package com.jacoblindev.updnfiledemo.controller;

import java.util.List;

import com.jacoblindev.updnfiledemo.helper.ExcelHelper;
import com.jacoblindev.updnfiledemo.helper.ResponseMessage;
import com.jacoblindev.updnfiledemo.model.Tutorial;
import com.jacoblindev.updnfiledemo.service.ExcelService;

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
                System.out.print(e);
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
}
