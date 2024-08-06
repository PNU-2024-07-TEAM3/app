package com.ll.sapp.studyroom;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyRoomService {
    @Autowired
    private StudyRoomMemberRepository studyRoomMemberRepository;
    @Autowired
    private StudyRoomRepository studyRoomRepository;


    // 스터디룸 개설
    public StudyRoom create() {
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
