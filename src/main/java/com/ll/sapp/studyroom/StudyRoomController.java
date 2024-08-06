package com.ll.sapp.studyroom;

import com.ll.sapp.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class StudyRoomController {
    private final StudyRoomService studyRoomService;
    private final StudyRoomMemberService studyRoomMemberService;

    @PostMapping("/enroll")
    public String enroll(@RequestParam Integer studyRoomId, @AuthenticationPrincipal SiteUser siteUser, Model model) {
        studyRoomMemberService.enrollUserInStudyRoom(studyRoomId, siteUser);
        model.addAttribute("message", "Successfully enrolled!");
        return "redirect:/myclassroom/list";
    }

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



