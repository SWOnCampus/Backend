package com.swOnCampus.AIPlatform.domain.consulting.repository;

import com.swOnCampus.AIPlatform.domain.consulting.entity.Consulting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultingRepository extends JpaRepository<Consulting, Long> {

}
