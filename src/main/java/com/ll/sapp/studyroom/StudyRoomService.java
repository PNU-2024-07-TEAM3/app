package com.ll.sapp.studyroom;

import com.ll.sapp.DataNotFoundException;
import com.ll.sapp.user.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class StudyRoomService {
    private final StudyRoomRepository studyRoomRepository;
    @Autowired
    private StudyRoomMemberRepository studyRoomMemberRepository;

    // 스터디룸 개설
    public void create(String title, String endDate, Integer numOfUser, SiteUser leader) {
        StudyRoom s = new StudyRoom();
        s.setTitle(title);
        s.setCreateDate(LocalDateTime.now());
        s.setEndDate(endDate);
        s.setNumOfUser(numOfUser);
        s.setLeader(leader);
        this.studyRoomRepository.save(s);
    }

    //스터디룸 수정
    public void modify(StudyRoom studyRoom, String title, String endDate, Integer numOfUser) {
        studyRoom.setTitle(title);
        studyRoom.setEndDate(endDate);
        studyRoom.setNumOfUser(numOfUser);
        this.studyRoomRepository.save(studyRoom);
    }

    public void delete(StudyRoom studyRoom) {
        this.studyRoomRepository.delete(studyRoom);
    }

    public Page<StudyRoom> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10,  Sort.by(sorts));
        Specification<StudyRoom> spec = search(kw);
        return this.studyRoomRepository.findAll(spec, pageable);
    }

    public StudyRoom getStudyRoom(Integer id) {
        Optional<StudyRoom> studyRoom = this.studyRoomRepository.findById(id);
        if (studyRoom.isPresent()) {
            return studyRoom.get();
        } else {
            throw new DataNotFoundException("studyRoom not found");
        }
    }


    public List<StudyRoom> getList() {
        return this.studyRoomRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<StudyRoom> getStudyRoomsByUserId(Integer userId) {
        List<StudyRoomMember> studyRoomMembers = studyRoomMemberRepository.findByMember_UserId(userId);
        return studyRoomMembers.stream()
                .map(StudyRoomMember::getStudyRoom)
                .distinct()
                .collect(Collectors.toList());
    }

    private Specification<StudyRoom> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<StudyRoom> s, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<StudyRoom, SiteUser> u1 = s.join("leader", JoinType.LEFT);
                return cb.or(cb.like(s.get("title"), "%" + kw + "%"), // 제목
                        cb.like(u1.get("nickname"), "%" + kw + "%"));   // 질문 작성자
            }
        };
    }
}
