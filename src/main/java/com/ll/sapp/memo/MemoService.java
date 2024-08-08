package com.ll.sapp.memo;

import com.ll.sapp.dailystudy.DailyStudy;
import com.ll.sapp.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;

    public List<Memo> getAllMemos() {
        return memoRepository.findAll();
    }

    public void saveMemo(Memo memo) {
        memoRepository.save(memo);
    }

    public Optional<Memo> getMemoById(Long id) {
        return memoRepository.findById(id);
    }

    public void deleteMemo(Long id) {
        memoRepository.deleteById(id);
    }

    public boolean userHasMemo(DailyStudy dailyStudy, SiteUser user) {
        return memoRepository.existsByDailyStudyAndUser(dailyStudy, user);
    }

    public Memo getMemo(Long id) {
        return memoRepository.findById(id).orElse(null);
    }

    public List<Memo> getMemosByDailyStudy(DailyStudy dailyStudy) {
        return memoRepository.findByDailyStudy(dailyStudy);
    }
}
