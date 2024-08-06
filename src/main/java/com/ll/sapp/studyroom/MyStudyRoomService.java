package com.ll.sapp.studyroom;

import com.ll.sapp.user.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyStudyRoomService {
    private final StudyRoomRepository studyRoomRepository;
    private final StudyRoomMemberRepository studyRoomMemberRepository;

    @Autowired
    public MyStudyRoomService(StudyRoomRepository studyRoomRepository, StudyRoomMemberRepository studyRoomMemberRepository) {
        this.studyRoomRepository = studyRoomRepository;
        this.studyRoomMemberRepository = studyRoomMemberRepository;
//        this.siteUser = siteUser;
    }

    public List<StudyRoom> getAllClasses() {
        return studyRoomRepository.findAll();  // 모든 StudyRoom을 가져오는 예시 메서드
    }

    public List<StudyRoom> getMyClasses(SiteUser user) {
        return studyRoomMemberRepository.findByUser(user).stream()
                .map(StudyRoomMember::getStudyRoom)
                .collect(Collectors.toList());
    }

//    public List<StudyRoom> getMyClasses(SiteUser user) {
//
//    }

//
//    // StudyRoom 저장
//    public StudyRoom saveStudyRoom(StudyRoom studyRoom) {
//        return studyRoomRepository.save(studyRoom);
//    }
//
//    // StudyRoom 조회
//    public Optional<StudyRoom> getStudyRoomById(Long id) {
//        return studyRoomRepository.findById(id);
//    }
//
//    // 모든 StudyRoom 조회
//    public List<StudyRoom> getAllStudyRooms() {
//        return studyRoomRepository.findAll();
//    }
//
//    // 제목으로 StudyRoom 조회
//    public List<StudyRoom> getStudyRoomsByTitle(String title) {
//        return studyRoomRepository.findByTitle(title);
//    }
//
//    // 오픈 여부로 StudyRoom 조회
//    public List<StudyRoom> getStudyRoomsByOpenStatus(Boolean isOpen) {
//        return studyRoomRepository.findByIsOpen(isOpen);
//    }
//
//    // 커스텀 메소드 호출 예
//    public List<StudyRoom> findStudyRoomsByCustomCriteria(String criteria) {
//        return studyRoomRepository.findStudyRoomsByCustomCriteria(criteria);
//    }


}
