package com.swOnCampus.AIPlatform.domain.consulting.service;

import com.swOnCampus.AIPlatform.domain.consulting.entity.Company;
import com.swOnCampus.AIPlatform.domain.consulting.entity.Consulting;
import com.swOnCampus.AIPlatform.domain.consulting.exception.ConsultingErrorCode;
import com.swOnCampus.AIPlatform.domain.consulting.repository.CompanyRepository;
import com.swOnCampus.AIPlatform.domain.consulting.repository.ConsultingRepository;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.ConsultingSave;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.CompanyInfoRequest;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.ConsultingRequest;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.response.ConsultingResponse;
import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import com.swOnCampus.AIPlatform.domain.member.repository.MemberRepository;
import com.swOnCampus.AIPlatform.global.exception.CommonErrorCode;
import com.swOnCampus.AIPlatform.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MemberRepository memberRepository;
    private final ConsultingRepository consultingRepository;
    private final ConsultingService consultingService;

    @Override
    public ConsultingResponse createOrGetConsulting(
        Long memberId,
        CompanyInfoRequest companyInfoRequest,
        Long companyId // 채팅방 id
    ) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new GlobalException(CommonErrorCode.NOT_EXIST_MEMBER));

        if (companyId == null) {
            companyId = createCompanyWithConsulting(member, companyInfoRequest);
        }
        Consulting consulting = consultingRepository.findByCompanyId(companyId)
            .orElseThrow(() -> new GlobalException(ConsultingErrorCode.NOT_EXIST_CONSULTING));

        return new ConsultingResponse(consulting.getSummary());
    }

    private Long createCompanyWithConsulting(Member member, CompanyInfoRequest companyInfoRequest) {
        Company company = Company.builder()
            .member(member)
            .name(companyInfoRequest.name())
            .companySize(companyInfoRequest.companySize())
            .industry(companyInfoRequest.industry())
            .painPoint(companyInfoRequest.painPoint())
            .build();
        companyRepository.save(company);

        ConsultingRequest consultingRequest = new ConsultingRequest(
            companyInfoRequest.industry(),
            companyInfoRequest.companySize(),
            companyInfoRequest.painPoint()
        );

        ConsultingSave result = consultingService.getConsultingResult(consultingRequest);

        Consulting consulting = Consulting.builder()
            .company(company)
            .result(result.result())
            .summary(result.summary())
            .build();
        consultingRepository.save(consulting);

        return consulting.getCompany().getId();
    }
}