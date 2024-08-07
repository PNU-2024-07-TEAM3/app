package com.ll.sapp.memo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/memo")
public class MemoController {
    private final MemoService memoService;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("memo", memoService.getAllMemos());
        return "memo_list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("memo", new Memo());
        return "memo_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Memo memo) {
        memoService.saveMemo(memo);
        return "redirect:/memo";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Memo> memo = memoService.getMemoById(id);
        if (memo.isPresent()) {
            model.addAttribute("memo", memo.get());
            return "memo_form";
        }
        return "redirect:/memo";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        memoService.deleteMemo(id);
        return "redirect:/memo";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Memo memo = memoService.getMemo(id);
        model.addAttribute("memo", memo);
        return "memo_detail";
    }
}