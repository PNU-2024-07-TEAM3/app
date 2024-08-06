package com.ll.sapp.studyroom;

import com.ll.sapp.user.SiteUser;
import com.ll.sapp.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/studyRooms")
public class StudyRoomController {
    private final UserService userService;
    private final StudyRoomService studyRoomService;

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
