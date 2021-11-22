package com.jacoblindev.updnfiledemo.service;

import java.util.List;

import com.jacoblindev.updnfiledemo.exception.ApiException;
import com.jacoblindev.updnfiledemo.helper.CSVHelper;
import com.jacoblindev.updnfiledemo.model.Tutorial;
import com.jacoblindev.updnfiledemo.repository.TutorialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVService {

    @Autowired
    TutorialRepository tutorialRepository;

    public void save(MultipartFile file) {
        try {
            List<Tutorial> tutorials = CSVHelper.csvTutorials(file.getInputStream());
            tutorialRepository.saveAll(tutorials);
        } catch (Exception e) {
            throw new ApiException("Fail to store CSV data: " + e.getMessage());
        }
    }

    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }
}
