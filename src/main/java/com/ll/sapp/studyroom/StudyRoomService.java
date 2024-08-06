package com.ll.sapp.studyroom;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.ll.sapp.user.SiteUser;
import com.ll.sapp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Store;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class StudyRoomService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 스터디룸 개설
    public StudyRoom create(String username, String nickname, String password) {
        return null;
    }

    private final StudyRoomRepository studyRoomRepository;

    public List<StudyRoom> getList() {
        return this.studyRoomRepository.findAll();
    }
}
