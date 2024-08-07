package com.ll.sapp.studyroom;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudyRoomModifyForm {
    @NotNull
    private Integer studyRoomId;

    @Size(min = 2, max = 20)
    @NotNull(message = "제목은 필수항목입니다.")
    private String title;

    @NotNull(message = "종료날짜는 필수항목입니다.")
    private String endDate;

    @NotNull(message = "학습 목적은 필수항목입니다.")
    private String learningObjective;

    @NotNull(message = "최대인원 설정은 필수항목입니다.")
    @Min(value = 1, message = "최소 1명 이상이어야 합니다.")
    private Integer numOfUser;
}
