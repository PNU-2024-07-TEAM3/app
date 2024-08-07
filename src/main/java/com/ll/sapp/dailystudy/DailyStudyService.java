package com.ll.sapp.dailystudy;

import com.ll.sapp.studyroom.StudyRoom;
import com.ll.sapp.studyroom.StudyRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DailyStudyService {
    @Autowired
    private DailyStudyRepository dailyStudyRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    public List<DailyStudy> getDailyStudyByStudyRoom(StudyRoom studyRoom) {
        return dailyStudyRepository.findByStudyRoom(studyRoom);
    }
    public List<DailyStudy> getDailyStudyByStudyRoomId(Integer studyRoomId) {
        return dailyStudyRepository.findByStudyRoom_StudyRoomId(studyRoomId);
    }

    public void saveDailyStudy(DailyStudy dailyStudy) {
        dailyStudyRepository.save(dailyStudy);
    }

    public List<DailyStudy> getList() {
        return this.dailyStudyRepository.findAll();
    }

    public List<DailyStudy> getListByStudyRoomId(Integer studyRoomId) {
        return dailyStudyRepository.findByStudyRoom_StudyRoomId(studyRoomId);
    }

    public DailyStudy addDailyStudy(Integer studyRoomId, String dailyStudyTitle) {
        StudyRoom studyRoom = studyRoomRepository.findById(studyRoomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid studyRoom ID:" + studyRoomId));

        DailyStudy dailyStudy = new DailyStudy();
        dailyStudy.setStudyRoom(studyRoom);
        dailyStudy.setDailyStudyTitle(dailyStudyTitle);

        return dailyStudyRepository.save(dailyStudy);
    }
}
