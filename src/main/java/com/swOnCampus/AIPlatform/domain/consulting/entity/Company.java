package com.swOnCampus.AIPlatform.domain.consulting.entity;

import com.swOnCampus.AIPlatform.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "company")
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String companySize;

    @Column(nullable = false)
    private String industry;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String painPoint;

    @Builder
    public Company(Long userId, String name, String companySize, String industry, String painPoint) {
        this.userId = userId;
        this.name = name;
        this.companySize = companySize;
        this.industry = industry;
        this.painPoint = painPoint;
    }
}
