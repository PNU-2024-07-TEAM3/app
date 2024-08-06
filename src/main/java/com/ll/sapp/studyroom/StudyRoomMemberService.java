package com.ll.sapp.studyroom;

import com.ll.sapp.user.SiteUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudyRoomMemberService {

    private final StudyRoomRepository studyRoomRepository;
    private final StudyRoomMemberRepository studyRoomMemberRepository;

    public StudyRoomMemberService(StudyRoomRepository studyRoomRepository, StudyRoomMemberRepository studyRoomMemberRepository) {
        this.studyRoomRepository = studyRoomRepository;
        this.studyRoomMemberRepository = studyRoomMemberRepository;
    }

    @Transactional
    public void enrollUserInStudyRoom(Integer studyRoomId, SiteUser siteUser) {
        StudyRoom studyRoom = studyRoomRepository.findById(studyRoomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid study room ID"));

        StudyRoomMember studyRoomMember = new StudyRoomMember();
        studyRoomMember.setStudyRoom(studyRoom);
        studyRoomMember.setUser(siteUser);

        studyRoomMemberRepository.save(studyRoomMember);
    }
}
