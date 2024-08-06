package com.ll.sapp.studyroom;

import com.ll.sapp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class StudyRoomService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;


    // 스터디룸 개설
    public StudyRoom create() {
        return null;
    }


    public List<StudyRoom> getList() {
        return this.studyRoomRepository.findAll();
    }
}
