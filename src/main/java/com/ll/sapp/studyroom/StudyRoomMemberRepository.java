package com.ll.sapp.studyroom;

import com.ll.sapp.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRoomMemberRepository extends JpaRepository<StudyRoomMember, Long> {
    List<StudyRoomMember> findByUser(SiteUser user);
}
