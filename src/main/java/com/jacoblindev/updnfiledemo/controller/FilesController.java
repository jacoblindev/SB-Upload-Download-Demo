package com.jacoblindev.updnfiledemo.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.jacoblindev.updnfiledemo.helper.ResponseMessage;
import com.jacoblindev.updnfiledemo.model.FileInfo;
import com.jacoblindev.updnfiledemo.service.FilesStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
@RequestMapping("/api/v1/file")
@CrossOrigin("http://localhost:8081")
public class FilesController {

    @Autowired
    FilesStorageService fsService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            fsService.save(file);
            return new ResponseEntity<>(
                    new ResponseMessage("Uploaded file successfully: " + file.getOriginalFilename() + "!"),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ResponseMessage("Couldn't upload the file: " + file.getOriginalFilename() + "!"),
                    HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getAllFiles() {
        List<FileInfo> fileInfos = fsService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
            return new FileInfo(filename, url);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(fileInfos, HttpStatus.OK);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fsService.load(filename);
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.set("Content-disposition", "attachment; filename=" + file.getFilename());
        return new ResponseEntity<>(file, resHeaders, HttpStatus.OK);
    }
}
