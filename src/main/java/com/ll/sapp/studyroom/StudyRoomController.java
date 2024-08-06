package com.ll.sapp.studyroom;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Controller
public class StudyRoomController {
    private final StudyRoomRepository studyRoomRepository;

    @GetMapping("classup/list")
    public String list(Model model) {

        List<StudyRoom> StudyRoomList = this.studyRoomRepository.findAll();
        model.addAttribute("StudyRoomList", StudyRoomList);
        return "studyroom_list";
        }
    }

