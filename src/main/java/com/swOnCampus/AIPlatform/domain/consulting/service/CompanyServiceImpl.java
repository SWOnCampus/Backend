package com.swOnCampus.AIPlatform.domain.consulting.service;

import com.swOnCampus.AIPlatform.domain.consulting.entity.Company;
import com.swOnCampus.AIPlatform.domain.consulting.exception.ConsultingErrorCode;
import com.swOnCampus.AIPlatform.domain.consulting.repository.CompanyRepository;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.CompanyInfoRequest;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.ConsultingRequest;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.response.ConsultingResponse;
import com.swOnCampus.AIPlatform.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ConsultingService consultingService;

    @Override
    public ConsultingResponse saveCompanyInfo(
        Long memberId,
        CompanyInfoRequest companyInfoRequest,
        boolean summary
    ) {
        if (!companyRepository.existsByUserId(memberId)) {
            throw new GlobalException(ConsultingErrorCode.NOT_EXIST_USER);
        }

        Company company = Company.builder()
            .userId(memberId)
            .name(companyInfoRequest.name())
            .companySize(companyInfoRequest.companySize())
            .industry(companyInfoRequest.industry())
            .painPoint(companyInfoRequest.painPoint())
            .build();
        companyRepository.save(company);

        ConsultingRequest consultingRequest = new ConsultingRequest(
            companyInfoRequest.companySize(),
            companyInfoRequest.industry(),
            companyInfoRequest.painPoint()
        );

        return consultingService.sendToAi(consultingRequest, summary);
    }
}
