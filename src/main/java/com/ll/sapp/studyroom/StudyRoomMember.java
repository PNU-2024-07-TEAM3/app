package com.ll.sapp.studyroom;

import com.ll.sapp.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
public class StudyRoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @ManyToOne
//    private StudyRoom studyRoom;
//    @ManyToOne
//    SiteUser member;

    @ManyToOne
    @JoinColumn(name = "studyroom_id")
    private StudyRoom studyRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SiteUser user;
}

