package com.ll.sapp.dailystudy;

import com.ll.sapp.studyroom.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyStudyRepository extends JpaRepository<DailyStudy, Integer> {
    List<DailyStudy> findByStudyRoom(StudyRoom studyRoom);
    List<DailyStudy> findByStudyRoom_StudyRoomId(Integer studyRoomId);

// TODO : 스터디룸 정보 이용해서 leader찾기
}
