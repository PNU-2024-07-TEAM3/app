package com.ll.sapp.studyroom;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyRoomForm {
    @NotEmpty(message="제목은 필수항목입니다.")
    @Size(max=100)
    private String title;

    @NotEmpty(message="종료 일자는 필수항목입니다. YYYYMMDD 형식으로 입력해주세요.")
    private String endDate;

    @NotNull(message="모집 인원은 필수항목입니다.")
    private Integer numOfUser;

}