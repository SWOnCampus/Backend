package com.swOnCampus.AIPlatform.domain.member.repository;

import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String loginId);
    boolean existsByEmail(String email);
}