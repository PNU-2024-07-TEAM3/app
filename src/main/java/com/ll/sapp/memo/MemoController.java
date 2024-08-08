package com.ll.sapp.memo;

import com.ll.sapp.dailystudy.DailyStudy;
import com.ll.sapp.dailystudy.DailyStudyService;
import com.ll.sapp.studyroom.StudyRoomService;
import com.ll.sapp.user.SiteUser;
import com.ll.sapp.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemoController {

    @Autowired
    private DailyStudyService dailyStudyService;

    @Autowired
    private StudyRoomService studyRoomService;

    @Autowired
    private UserService userService;

    private final MemoService memoService;

    @GetMapping("studyRooms/{studyRoomId}/dailyStudy/{dailyStudyId}/memos")
    public String list(@PathVariable Integer studyRoomId, @PathVariable Integer dailyStudyId, Model model, Principal principal) {
        DailyStudy dailyStudy = dailyStudyService.getDailyStudy(dailyStudyId);
        model.addAttribute("memos", memoService.getMemosByDailyStudy(dailyStudy));
        model.addAttribute("studyRoomId", studyRoomId);
        model.addAttribute("dailyStudyId", dailyStudyId);

        String username = principal.getName();
        Optional<SiteUser> userOptional = userService.findByUsername(username);
        model.addAttribute("alreadyDone", memoService.userHasMemo(userOptional.get()));

        return "memo_list";
    }

    @GetMapping("/studyRooms/{studyRoomId}/dailyStudy/{dailyStudyId}/memos/create")
    public String createForm(MemoForm memoForm, @PathVariable Integer studyRoomId, @PathVariable Integer dailyStudyId, Model model) {
        model.addAttribute("memo", new Memo());
        model.addAttribute("studyRoomId", studyRoomId);
        model.addAttribute("dailyStudyId", dailyStudyId);
        return "memo_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/studyRooms/{studyRoomId}/dailyStudy/{dailyStudyId}/memos/create")
    public String save(@Valid MemoForm memoForm, BindingResult bindingResult, @PathVariable Integer studyRoomId, @PathVariable Integer dailyStudyId, Principal principal, Model model) {
        String username = principal.getName();
        Optional<SiteUser> userOptional = userService.findByUsername(username);
        SiteUser user = userOptional.get();

        if ( bindingResult.hasErrors() ) {
            return "memo_form";
        }

        // Check if the user already has a memo
        if (memoService.userHasMemo(user)) {
            bindingResult.reject("saveFailed", "메모는 하나만 작성하실 수 있습니다.");
            return "memo_form";
        }

        DailyStudy dailyStudy = dailyStudyService.getDailyStudy(dailyStudyId);
        Memo memo = new Memo();
        memo.setTitle(memoForm.getTitle());
        memo.setContent(memoForm.getContent());
        memo.setDailyStudy(dailyStudy);
        memo.setUser(user);
        memoService.saveMemo(memo);

        return "redirect:/studyRooms/" + studyRoomId + "/dailyStudy/" + dailyStudyId + "/memos";
    }

    @GetMapping("/studyRooms/{studyRoomId}/dailyStudy/{dailyStudyId}/memos/{memoId}/edit")
    public String editForm(MemoForm memoForm, @PathVariable Long memoId, Model model, @PathVariable Integer studyRoomId, @PathVariable Integer dailyStudyId) {
        Optional<Memo> memo = memoService.getMemoById(memoId);
        if (memo.isPresent()) {
            model.addAttribute("memo", memo.get());
            memoForm.setTitle(memo.get().getTitle());
            memoForm.setContent(memo.get().getContent());
            //애초에 하나 가지고 있다면 만들기 버튼이 없어야함
            return "memo_form";
        }
        return "redirect:/studyRooms/" + studyRoomId + "/dailyStudy/" + dailyStudyId + "/memos";
    }

    @PostMapping("/studyRooms/{studyRoomId}/dailyStudy/{dailyStudyId}/memos/{memoId}/edit")
    public String editForm(@Valid MemoForm memoForm, BindingResult bindingResult, @PathVariable Long memoId, Model model, @PathVariable Integer studyRoomId, @PathVariable Integer dailyStudyId) {
        if ( bindingResult.hasErrors() ) {
            return "memo_form";
        }

        Memo memo = memoService.getMemoById(memoId).get();
        memo.setTitle(memoForm.getTitle());
        memo.setContent(memoForm.getContent());
        memoService.saveMemo(memo);

        return "redirect:/studyRooms/" + studyRoomId + "/dailyStudy/" + dailyStudyId + "/memos";
    }

    @PostMapping("/studyRooms/{studyRoomId}/dailyStudy/{dailyStudyId}/memos/{memoId}/delete")
    public String delete(@PathVariable Long memoId, Model model, @PathVariable Integer studyRoomId, @PathVariable Integer dailyStudyId) {
        memoService.deleteMemo(memoId);
        return "redirect:/studyRooms/" + studyRoomId + "/dailyStudy/" + dailyStudyId + "/memos";
    }

    @GetMapping("/studyRooms/{studyRoomId}/dailyStudy/{dailyStudyId}/memos/{memoId}/detail")
    public String detail(@PathVariable Long memoId, @PathVariable Integer studyRoomId, @PathVariable Integer dailyStudyId, Model model) {
        Memo memo = memoService.getMemo(memoId);
        model.addAttribute("memo", memo);
        return "memo_detail";
    }
}
