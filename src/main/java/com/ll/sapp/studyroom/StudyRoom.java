package com.ll.sapp.studyroom;

import com.ll.sapp.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class StudyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyroom_id;

    private String title;

    private LocalDate endDate;
    private String learningObjective;

    private boolean isOpen;

    private LocalDate createDate;

    private int numOfUser;

    @ManyToMany(mappedBy = "studies")
    private Set<SiteUser> members = new HashSet<>();

}
