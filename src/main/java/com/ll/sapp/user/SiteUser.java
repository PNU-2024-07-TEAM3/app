package com.ll.sapp.user;

import com.ll.sapp.studyroom.StudyRoom;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true)
    private String userName;

    private String password;

    @OneToMany(mappedBy = "leader")
    private Set<StudyRoom> leadingRooms;

    @Column(unique = true)
    private String nickname;
}