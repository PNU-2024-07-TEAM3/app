package com.ll.sapp.studyroom;

import com.ll.sapp.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
public class StudyRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long StudyRoomId;

    private String title;
    private LocalDateTime createDate;
    private LocalDateTime endDate;
    private String learningObjective;
    private Integer numOfUser;
    private Boolean isOpen;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private SiteUser leader;

    @OneToMany(mappedBy = "studyRoom")
    private List<StudyRoomMember> members;
}