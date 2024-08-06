package com.ll.sapp.studyroom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRoomRepository extends JpaRepository<StudyRoom, Integer> {
    StudyRoom findByTitle(String title);
    List<StudyRoom> findByTitleLike(String title);
    Page<StudyRoom> findAll(Pageable pageable);
    Page<StudyRoom> findAll(Specification<StudyRoom> spec, Pageable pageable);
}
