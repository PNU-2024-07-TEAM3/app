package com.ll.sapp.studyroom;

import com.ll.sapp.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public interface StudyRoomMemberRepository extends JpaRepository<StudyRoomMember, Long> {
    List<StudyRoomMember> findByUser(SiteUser user);
    List<StudyRoomMember> findByMember_UserId(Integer userId); // Access the `userId` of the `member` field
}



