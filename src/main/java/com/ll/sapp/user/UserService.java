package com.ll.sapp.user;

import com.ll.sapp.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String userName, String nickname, String password) {
        SiteUser user = new SiteUser();
        user.setUserName(userName);
        user.setNickname(nickname);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String userName) {
        Optional<SiteUser> siteUser = this.userRepository.findByUserName(userName);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    public SiteUser getUser(Integer userId) {
        Optional<SiteUser> siteUser = this.userRepository.findByUserId(userId);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteUser not found");
        }
    }

    public Optional<SiteUser> findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public Optional<SiteUser> findByUserId(Integer userId) {
        return userRepository.findByUserId(userId);
    }
}