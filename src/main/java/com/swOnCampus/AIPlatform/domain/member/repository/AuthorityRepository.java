package com.swOnCampus.AIPlatform.domain.member.repository;

import com.swOnCampus.AIPlatform.domain.member.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
