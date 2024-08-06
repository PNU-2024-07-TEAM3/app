package com.ll.sapp.studyroom;

import com.ll.sapp.user.SiteUser;
import com.ll.sapp.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;



@Controller
@RequiredArgsConstructor
@RequestMapping("/studyRooms")
public class StudyRoomController {
    private final UserService userService;
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


    @GetMapping("/myList")
    @PreAuthorize("isAuthenticated()")
    public String myList(Principal principal, Model model) {
        String username = principal.getName();
        Optional<SiteUser> user = userService.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        List<StudyRoom> studyRoomsByUserId = studyRoomService.getStudyRoomsByUserId(user.get().getUserId());
        model.addAttribute("studyrooms", studyRoomsByUserId);
        return "my_studyroom_list";
    }
}
