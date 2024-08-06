package com.ll.sapp.studyroom;

import com.ll.sapp.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudyRoomService {

    // 스터디룸 개설
    public StudyRoom create(String title, ) {
        SiteUser user = new SiteUser();
        user.setUserName(username);
        user.setNickname(nickname);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }
}
