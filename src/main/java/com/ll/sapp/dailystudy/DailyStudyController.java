package com.ll.sapp.dailystudy;

import com.ll.sapp.studyroom.StudyRoom;
import com.ll.sapp.studyroom.StudyRoomCreateForm;
import com.ll.sapp.dailystudy.DailyStudy;
import com.ll.sapp.studyroom.StudyRoomService;
import com.ll.sapp.dailystudy.DailyStudyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
public class DailyStudyController {

    @Autowired
    private DailyStudyService dailyStudyService;

    @Autowired
    private StudyRoomService studyRoomService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/studyRooms/{studyRoomId}/dailyStudy/list")
    public String dailystudylist(@PathVariable("studyRoomId") Integer studyRoomId, Model model) {
        List<DailyStudy> dailyStudyList = this.dailyStudyService.getListByStudyRoomId(studyRoomId);
        StudyRoom studyRoom = studyRoomService.getStudyRoom(studyRoomId);
        model.addAttribute("dailyStudyList", dailyStudyList);
        model.addAttribute("studyRoom", studyRoom); // StudyRoom을 모델에 추가
        return "studyroom_dailystudy_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/studyRooms/{studyRoomId}/dailyStudy/add")
    public String addDailyStudyForm(@PathVariable("studyRoomId") Integer studyRoomId, DailyStudyCreateForm dailyStudyCreateForm, Model model) {
        model.addAttribute("studyRoomId", studyRoomId); // StudyRoomId를 모델에 추가
        return "studyroom_dailystudy_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/studyRooms/{studyRoomId}/dailyStudy/add")
    public String addDailyStudy(@PathVariable("studyRoomId") Integer studyRoomId, @Valid DailyStudyCreateForm dailyStudyCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "studyroom_dailystudy_form";
        }

        dailyStudyService.addDailyStudy(studyRoomId, dailyStudyCreateForm.getDailyStudyTitle());

        return "redirect:/studyRooms/" + studyRoomId + "/dailyStudy/list";
    }
}
