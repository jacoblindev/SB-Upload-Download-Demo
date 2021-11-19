package com.jacoblindev.updnfiledemo.repository;

import com.jacoblindev.updnfiledemo.model.Tutorial;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

}
