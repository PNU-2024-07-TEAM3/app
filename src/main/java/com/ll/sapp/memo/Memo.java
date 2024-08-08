package com.ll.sapp.memo;

import com.ll.sapp.dailystudy.DailyStudy;
import com.ll.sapp.user.SiteUser;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoId;

    private String title;

    @Column(length = 1000)
    private String content;

    @ManyToOne
    private SiteUser user;

    @ManyToOne
    private DailyStudy dailyStudy;
}
