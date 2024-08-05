package com.ll.sapp.user;

import com.ll.sapp.studyroom.Studyroom;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String nickname;

    @ManyToMany
    @JoinTable(
            name = "user_study",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "studyroom_id")
    )
    private Set<Studyroom> studies = new HashSet<>();


//
//    @Column(unique = true)
//    private String email;
}
