package com.swOnCampus.AIPlatform.domain.consulting.entity;

import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import com.swOnCampus.AIPlatform.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "consulting")
public class Consulting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company; // 기업 입력 정보 (채팅방)

    @Column(columnDefinition = "TEXT")
    private String result;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT", name = "consulting_all")
    private String consultingAll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member", nullable = false)
    private Member member;

    @Builder
    public Consulting(Company company, String result, String summary, String consultingAll, Member member) {
        this.company = company;
        this.result = result;
        this.summary = summary;
        this.consultingAll = consultingAll;
        this.member = member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
