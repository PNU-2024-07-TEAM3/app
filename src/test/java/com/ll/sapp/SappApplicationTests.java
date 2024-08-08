package com.ll.sapp;

import com.ll.sapp.studyroom.StudyRoom;
import com.ll.sapp.studyroom.StudyRoomRepository;
import com.ll.sapp.studyroom.StudyRoomService;
import com.ll.sapp.user.UserService;
import org.antlr.v4.runtime.misc.LogManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
class SappApplicationTests {
//    private StudyRoomService studyRoomService;
//
//    @Test
//    @Transactional
//    void myTest() {
//        StudyRoom studyRoom = new StudyRoom();
//        studyRoom.setTitle("title");
//        studyRoom.setCreateDate(LocalDateTime.now());
//        studyRoom.setEndDate(LocalDateTime.now());
//        studyRoom.setLearningObjective("Asdf");
//        studyRoom.setNumOfUser(12);
//        studyRoom.setIsOpen(false);
////        userService.create("test", "test", "1234");
//        userService.getUser("test").getStudyRooms().add(studyRoom);
//    }
}
