package com.ll.sapp.studyroom;

import com.ll.sapp.user.SiteUser;
import com.ll.sapp.user.UserRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class StudyRoomService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;
    @Autowired
    private StudyRoomMemberRepository studyRoomMemberRepository;

    public StudyRoom create(String title, LocalDate endDate, String learningObjective, Integer numOfUser, SiteUser user) {
        StudyRoom studyRoom = new StudyRoom();
        studyRoom.setTitle(title);
        studyRoom.setCreateDate(LocalDateTime.now());
        studyRoom.setEndDate(endDate);
        studyRoom.setLearningObjective(learningObjective);
        studyRoom.setNumOfUser(numOfUser);
        studyRoom.setIsOpen(false);
        studyRoom.setLeader(user);
        studyRoomRepository.save(studyRoom);
        StudyRoomMember studyRoomMember = new StudyRoomMember();
        studyRoomMember.setStudyRoom(studyRoom);
        studyRoomMember.setMember(user);
        studyRoomMemberRepository.save(studyRoomMember);
        return studyRoom;
    }

    /*
    //스터디룸 수정
    //Todo 수정
    public void modify(StudyRoom studyRoom, String title, String endDate, Integer numOfUser) {
        studyRoom.setTitle(title);
        studyRoom.setEndDate(endDate);
        studyRoom.setNumOfUser(numOfUser);
        this.studyRoomRepository.save(studyRoom);
    }
     */

    public void delete(StudyRoom studyRoom) {
        this.studyRoomRepository.delete(studyRoom);
    }

    public Page<StudyRoom> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<StudyRoom> spec = search(kw);
        return this.studyRoomRepository.findAll(spec, pageable);
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

    public StudyRoom getStudyRoom(Integer id) {
        return this.studyRoomRepository.findStudyRoomByStudyRoomId(id);
    }

    public void enrollUserInStudyRoom(Integer userId, Integer studyRoomId) {
        Optional<SiteUser> userOpt = userRepository.findById(userId);
        Optional<StudyRoom> studyRoomOpt = studyRoomRepository.findById(studyRoomId);

        if (userOpt.isPresent() && studyRoomOpt.isPresent()) {
            SiteUser user = userOpt.get();
            StudyRoom studyRoom = studyRoomOpt.get();
            StudyRoomMember studyRoomMember = new StudyRoomMember();
            studyRoomMember.setStudyRoom(studyRoom);
            studyRoomMember.setMember(user);
            studyRoomMemberRepository.save(studyRoomMember);
        } else {
            throw new RuntimeException("User or StudyRoom not found");
        }
    }

    public void saveModifyInfo(String title, String endDate, Integer numOfUser, String learningObjective, StudyRoom studyRoom) {
        studyRoom.setTitle(title);
        studyRoom.setEndDate(LocalDate.parse(endDate));
        studyRoom.setNumOfUser(numOfUser);
        studyRoom.setLearningObjective(learningObjective);
        studyRoomRepository.save(studyRoom);
    }

    public void completeRecruit(StudyRoom studyRoom) {
        studyRoom.setIsOpen(true);
        studyRoomRepository.save(studyRoom);
    }

    public boolean isAlreadyEnrolled(SiteUser siteUser, StudyRoom studyRoom) {
        return studyRoomMemberRepository.existsByMemberAndStudyRoom(siteUser, studyRoom);
    }
}
