package com.ll.sapp.studyroom;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class Studyroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long study_id;

    private String title;

    private LocalDate endDate;
    private String learningObjective;

    private boolean isOpen;

    private LocalDate createDate;

    private int numOfUser;

}
