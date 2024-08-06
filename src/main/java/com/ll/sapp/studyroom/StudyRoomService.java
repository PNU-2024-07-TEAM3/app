package com.ll.sapp.studyroom;

import com.ll.sapp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class StudyRoomService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;
    @Autowired
    private StudyRoomMemberRepository studyRoomMemberRepository;

    // 스터디룸 개설
    public StudyRoom create(String username, String nickname, String password) {
        return null;
    }


    public List<StudyRoom> getList() {
        return this.studyRoomRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<StudyRoom> getStudyRoomsByUserId(Integer userId) {
        List<StudyRoomMember> studyRoomMembers = studyRoomMemberRepository.findByMember_UserId(userId);
        return studyRoomMembers.stream()
                .map(StudyRoomMember::getStudyRoom)
                .distinct()
                .collect(Collectors.toList());
    }
}
