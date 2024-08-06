package com.ll.sapp.studyroom;

import java.security.Principal;
import java.util.List;

import com.ll.sapp.user.SiteUser;
import com.ll.sapp.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/studyRooms")
public class StudyRoomController {
    private final StudyRoomService studyRoomService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<StudyRoom> paging = this.studyRoomService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "studyroom_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        StudyRoom studyRoom = this.studyRoomService.getStudyRoom(id);
        model.addAttribute("studyRoom", studyRoom);
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String studyRoomCreate(StudyRoomForm studyRoomForm) {
        return "studyroom_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String studyRoomCreate(@Valid StudyRoomForm studyRoomForm,
                                 BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {return "studyroom_form";}
        SiteUser leader = this.userService.getUser(principal.getName());
        this.studyRoomService.create(studyRoomForm.getTitle(), studyRoomForm.getEndDate(), studyRoomForm.getNumOfUser(), leader);
        return "redirect:/studyRooms/list";
    }

}



