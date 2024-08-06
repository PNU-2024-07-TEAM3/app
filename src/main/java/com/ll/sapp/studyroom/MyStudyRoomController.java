package com.ll.sapp.studyroom;

import com.ll.sapp.user.SiteUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyStudyRoomController {

    private final MyStudyRoomService myStudyRoomService;
    public MyStudyRoomController(MyStudyRoomService myStudyRoomService) {
        this.myStudyRoomService = myStudyRoomService;
    }

//    @GetMapping("/myclassroom/list")
//    public String showMyClassRoomList(Model model) {
//
//        model.addAttribute("Classes", myStudyRoomService.getAllClasses());
//        return "myclassroom_list";
////        return "HelloWorld";
//    }

    @GetMapping("/myclassroom/list")
    public String showMyClassRoomList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SiteUser user = (SiteUser) authentication.getPrincipal(); // 현재 로그인한 사용자 가져오기
        model.addAttribute("Classes", myStudyRoomService.getMyClasses(user));
        return "myclassroom_list";
    }

}
