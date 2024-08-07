package com.ll.sapp.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Integer> {
    Optional<SiteUser> findByUserName(String userName);

    Optional<SiteUser> findByUserId(Integer userId);
}
