package com.ll.sapp.dailystudy;

import com.ll.sapp.studyroom.StudyRoom;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DailyStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dailyStudyId;

    private String dailyStudyTitle;

    @ManyToOne
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;

}