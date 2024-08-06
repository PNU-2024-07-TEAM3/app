package com.ll.sapp.studyroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRoomMemberRepository extends JpaRepository<StudyRoomMember, Integer> {
    List<StudyRoomMember> findByMember_UserId(Integer userId); // Access the `userId` of the `member` field
}
