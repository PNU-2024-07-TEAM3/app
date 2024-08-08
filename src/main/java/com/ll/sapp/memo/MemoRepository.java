package com.ll.sapp.memo;

import com.ll.sapp.dailystudy.DailyStudy;
import com.ll.sapp.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
    boolean existsByUser(SiteUser user);

    List<Memo> findByDailyStudy(DailyStudy dailyStudy);

    // 새로운 메서드 추가
    boolean existsByDailyStudyAndUser(DailyStudy dailyStudy, SiteUser user);
}

