package com.swOnCampus.AIPlatform.domain.consulting.web.controller;

import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.CompanyInfoRequest;
import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import com.swOnCampus.AIPlatform.global.annotation.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/consulting")
public class ConsultingController {

    @PostMapping()
    public void createConsulting(
        @LoginMember Member member,
        @RequestBody CompanyInfoRequest companyInfoRequest,
        @RequestParam(defaultValue = "false") boolean summary
    ) {
    }
}
