package com.ll.sapp.studyroom;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class StudyRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer StudyRoomId;

    private String title;
    private LocalDateTime createDate;
    private LocalDateTime endDate;
    private String learningObjective;
    private Integer numOfUser;
    private Boolean isOpen;
}