package com.ll.sapp.memo;

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

    public Optional<Memo> getMemoById(Long id) {
        return memoRepository.findById(id);
    }

    public Memo saveMemo(Memo memo) {
        return memoRepository.save(memo);
    }

    public void deleteMemo(Long id) {
        memoRepository.deleteById(id);
    }

    public Memo getMemo(Long id) {
        return memoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid memo Id:" + id));
    }
}