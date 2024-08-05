package com.ll.sapp.studyroom;

<<<<<<< HEAD
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
=======
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

>>>>>>> 726adeb67d229daa0d3c27e557837127e8b14f46
public class Studyroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    private Long studyroom_id;
=======
    private Long study_id;
>>>>>>> 726adeb67d229daa0d3c27e557837127e8b14f46

    private String title;

    private LocalDate endDate;
    private String learningObjective;

    private boolean isOpen;

    private LocalDate createDate;

    private int numOfUser;

<<<<<<< HEAD
    @ManyToMany(mappedBy = "studies")
    private Set<SiteUser> members = new HashSet<>();

=======
>>>>>>> 726adeb67d229daa0d3c27e557837127e8b14f46
}
