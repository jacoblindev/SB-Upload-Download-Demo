package com.jacoblindev.updnfiledemo.repository;

import java.util.List;

import com.jacoblindev.updnfiledemo.model.Tutorial;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByPublished(int published);

    List<Tutorial> findByTitleContaining(String title);

}
