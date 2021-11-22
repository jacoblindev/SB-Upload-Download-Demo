package com.jacoblindev.updnfiledemo.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.jacoblindev.updnfiledemo.exception.ApiException;
import com.jacoblindev.updnfiledemo.helper.ExcelHelper;
import com.jacoblindev.updnfiledemo.model.Tutorial;
import com.jacoblindev.updnfiledemo.repository.TutorialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelService {
    @Autowired
    TutorialRepository tutorialRepository;

    public void save(MultipartFile file) {
        try {
            List<Tutorial> tutorials = ExcelHelper.excelTutorials(file.getInputStream());
            tutorialRepository.saveAll(tutorials);
        } catch (IOException e) {
            throw new ApiException("Fail to store excel data: " + e.getMessage());
        }
    }

    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }

    public ByteArrayInputStream load() {
        List<Tutorial> tutorials = tutorialRepository.findAll();
        return ExcelHelper.tutorialsToExcel(tutorials);
    }

    public ByteArrayInputStream loadTemplate() {
        return ExcelHelper.templateToExcel();
    }
}
