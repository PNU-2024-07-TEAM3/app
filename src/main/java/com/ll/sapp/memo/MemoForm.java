package com.ll.sapp.memo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MemoForm {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 4, message = "내용을 4자 이상 입력해주세요.")
    @Size(max = 200, message = "내용을 200자 이하로 입력해주세요.")
    private String content;
}
