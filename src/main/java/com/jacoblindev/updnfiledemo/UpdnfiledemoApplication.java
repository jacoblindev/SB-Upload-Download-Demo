package com.jacoblindev.updnfiledemo;

import javax.annotation.Resource;

import com.jacoblindev.updnfiledemo.service.FilesStorageService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UpdnfiledemoApplication implements CommandLineRunner {
	@Resource
	FilesStorageService fsService;

	public static void main(String[] args) {
		SpringApplication.run(UpdnfiledemoApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		fsService.deleteAll();
		fsService.init();
	}

}
