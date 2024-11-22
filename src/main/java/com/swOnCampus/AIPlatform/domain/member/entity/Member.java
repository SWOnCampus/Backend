package com.swOnCampus.AIPlatform.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "business_num")
    private String businessNum;

    @Column(name = "corporation")
    private String corporation;

    @Column(name = "sign_up_route")
    private String signUpRoute;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Authority> authorityList;

    public void addRole(Authority authority) {
        authorityList.add(authority);
        authority.setMember(this);
    }
}