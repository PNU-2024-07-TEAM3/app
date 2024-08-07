package com.ll.sapp.dailystudy;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DailyStudyCreateForm {
    @Size(min = 2, max = 50)
    @NotNull(message = "제목은 필수항목입니다.")
    private String dailyStudyTitle;
}
