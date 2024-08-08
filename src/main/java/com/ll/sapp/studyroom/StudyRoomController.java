package com.ll.sapp.studyroom;

import com.ll.sapp.user.SiteUser;
import com.ll.sapp.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Controller
@RequestMapping("/studyRooms")
public class StudyRoomController {
    private final StudyRoomService studyRoomService;
    private final UserService userService;

    @Autowired
    private StudyRoomMemberRepository studyRoomMemberRepository;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<StudyRoom> paging = this.studyRoomService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "studyroom_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, Principal principal) {
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        model.addAttribute("studyRoom", studyRoom);
        boolean isAlreadyEnrolled = false;
        if (principal != null) {
            String name = principal.getName();
            Optional<SiteUser> user = userService.findByUsername(name);
            SiteUser siteUser = user.get();
            isAlreadyEnrolled = studyRoomService.isAlreadyEnrolled(siteUser, studyRoom);
        }
        model.addAttribute("isAlreadyEnrolled", isAlreadyEnrolled);
        return "studyroom_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String studyRoomModify(StudyRoomModifyForm studyRoomModifyForm, @PathVariable("id") Integer id, Principal principal) {
        StudyRoom studyRoom = this.studyRoomService.getStudyRoom(id);
        if (!studyRoom.getLeader().getUserName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        studyRoomModifyForm.setStudyRoomId(id);
        studyRoomModifyForm.setTitle(studyRoom.getTitle());
        studyRoomModifyForm.setEndDate(studyRoom.getEndDate().toString());
        studyRoomModifyForm.setNumOfUser(studyRoom.getNumOfUser());
        studyRoomModifyForm.setLearningObjective(studyRoom.getLearningObjective());
        return "modify_studyroom_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@ModelAttribute StudyRoomModifyForm studyRoomModifyForm, @PathVariable("id") Integer id, Principal principal) {
        StudyRoom studyRoom = this.studyRoomService.getStudyRoom(id);
        if (!studyRoom.getLeader().getUserName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        }
        this.studyRoomService.saveModifyInfo(studyRoomModifyForm.getTitle(),
                studyRoomModifyForm.getEndDate(),
                studyRoomModifyForm.getNumOfUser(),
                studyRoomModifyForm.getLearningObjective(),
                studyRoom);
        return "redirect:/studyRooms/list";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        StudyRoom studyRoom = this.studyRoomService.getStudyRoom(id);
        if (!studyRoom.getLeader().getUserName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.studyRoomService.delete(studyRoom);
        return "redirect:/studyRooms/list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/completeRecruit/{id}")
    public String recruitComplete(Principal principal, @PathVariable("id") Integer id) {
        StudyRoom studyRoom = this.studyRoomService.getStudyRoom(id);
        if (!studyRoom.getLeader().getUserName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
        this.studyRoomService.completeRecruit(studyRoom);
        return "redirect:/studyRooms/list";
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

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String createStudyRoom(StudyRoomCreateForm studyRoomCreateForm) {
        return "create_studyroom_form";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String createStudyRoom(@Valid StudyRoomCreateForm studyRoomCreateForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "create_studyroom_form";
        }

        String userName = principal.getName();
        Optional<SiteUser> user = userService.findByUsername(userName);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        SiteUser siteUser = user.get();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate endDate = LocalDate.parse(studyRoomCreateForm.getEndDate(), formatter);
            studyRoomService.create(studyRoomCreateForm.getTitle(), endDate, studyRoomCreateForm.getLearningObjective(), studyRoomCreateForm.getNumOfUser(), siteUser);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("createFailed", "이미 등록된 Title 입니다.");
            return "create_studyroom_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("createFailed", e.getMessage());
            return "create_studyroom_form";
        }
        return "redirect:/studyRooms/list";
    }

    @PostMapping("enroll")
    @PreAuthorize("isAuthenticated()")
    public String enrollment(@RequestParam Integer studyRoomId, Principal principal) {
        String userName = principal.getName();
        Optional<SiteUser> user = userService.findByUsername(userName);
        if (user.isPresent()) {
            SiteUser siteUser = user.get();
            studyRoomService.enrollUserInStudyRoom(siteUser.getUserId(), studyRoomId);
        } else {
            throw new RuntimeException("User not found");
        }
        return "redirect:/studyRooms/list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/complete/{id}")
    public String complete(Model model, @PathVariable("id") Integer id) {
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        model.addAttribute("studyRoom", studyRoom);
        studyRoomService.completeStudyRoom(id);
        return "complete_page";
    }

}
