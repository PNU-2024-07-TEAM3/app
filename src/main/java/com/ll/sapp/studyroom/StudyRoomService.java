package com.ll.sapp.studyroom;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;

    public List<StudyRoom> getList() {
        return this.studyRoomRepository.findAll();
    }
}
