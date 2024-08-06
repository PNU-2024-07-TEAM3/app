package com.ll.sapp.studyroom;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Controller
public class StudyRoomController {
    private final StudyRoomService studyRoomService;

    @GetMapping("studyroom/list")
    public String list(Model model) {

        List<StudyRoom> StudyRoomList = this.studyRoomService.getList();
        model.addAttribute("StudyRoomList", StudyRoomList);
        return "studyroom_list";
        }

    @GetMapping(value = "/studyroom/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        return "studyroom_detail";
    }
    }



