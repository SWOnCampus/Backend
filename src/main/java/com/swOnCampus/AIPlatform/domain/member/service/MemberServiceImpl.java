package com.swOnCampus.AIPlatform.domain.member.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swOnCampus.AIPlatform.domain.consulting.entity.Company;
import com.swOnCampus.AIPlatform.domain.consulting.entity.Consulting;
import com.swOnCampus.AIPlatform.domain.member.entity.Authority;
import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import com.swOnCampus.AIPlatform.domain.member.repository.MemberRepository;
import com.swOnCampus.AIPlatform.domain.member.web.dto.LoginRequestDto;
import com.swOnCampus.AIPlatform.domain.member.web.dto.LoginResponseDto;
import com.swOnCampus.AIPlatform.domain.member.web.dto.SignUpRequestDto;
import com.swOnCampus.AIPlatform.domain.member.web.dto.SignUpResponseDto;
import com.swOnCampus.AIPlatform.global.security.jwt.JwtTokenProvider;
import com.swOnCampus.AIPlatform.domain.member.enums.Authorities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RestTemplate restTemplate;

    @Value(value = "${spring.api.businessCheck.encodingKey}")
    private String encodingKey;

    @Value(value = "${spring.api.businessCheck.decodingKey}")
    private String decodingKey;

    // Email 중복 검사 함수
    @Override
    public Boolean isExistEmail(String email) {
        boolean findMember = memberRepository.existsByEmail(email);

        if(findMember) {
            return true;
        }

        return false;
    }

    // 사업자번호 인증 함수
    @Override
    public Boolean isExistCorporation(String corporation) {
        String num = corporation.replace("-", "");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> body = new HashMap<>();
        body.put("b_no", List.of(num));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        String url = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=" + encodingKey;

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(new URI(url), entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), new TypeReference<>() {});

                System.out.println("Response: " + responseBody);

                List<Map<String, Object>> dataList = (List<Map<String, Object>>) responseBody.get("data");
                if (dataList != null && !dataList.isEmpty()) {
                    Map<String, Object> businessInfo = dataList.get(0);
                    String status = (String) businessInfo.get("tax_type");
                    if(!status.equals("국세청에 등록되지 않은 사업자등록번호입니다.")){
                        return true;
                    }
                }
            }
            return false;

        } catch (Exception e) {
            // 에러 처리
            e.printStackTrace();
            return false;
        }
    }


    // 회원가입 함수
    @Override
    public SignUpResponseDto.SignUpResponse signUp(SignUpRequestDto.SignupRequest request) {
        // 1. Member 객체 생성 및 초기화
        Member newMember = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .businessNum(request.getBusinessNum())
                .signUpRoute(request.getSignupRoute())
                .companies(new ArrayList<>()) // 초기화된 리스트
                .authorityList(new ArrayList<>()) // 초기화된 리스트
                .consultingList(new ArrayList<>()) // 초기화된 리스트
                .build();

        // 2. Authority 생성 및 추가
        Authority authority = Authority.builder()
                .type(Authorities.ROLE_ADMIN)
                .build();
        newMember.addRole(authority);

        // 3. Company 생성 및 추가
        Company company = Company.builder()
                .name(request.getName())
                .companySize("")
                .industry("")
                .painPoint("")
                .build();

        // Member와 Company 연결
        newMember.addCompany(company);

        // 4. Consulting 생성 및 추가
        Consulting consulting = Consulting.builder()
                .company(company)
                .member(newMember)
                .build();
        newMember.addConsulting(consulting);

        // 5. Member 저장
        memberRepository.save(newMember);

        // 6. 응답 생성
        return SignUpResponseDto.SignUpResponse.builder()
                .name(newMember.getName())
                .build();
    }

    // 로그인 함수
    @Override
    public LoginResponseDto.LoginResponse login(LoginRequestDto.LoginRequest request) {
        log.info("::email : {}::", request.getEmail());
        log.info("::password : {}::", request.getPassword());

        if(!memberRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }

        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(RuntimeException::new);

        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        List<Authority> authorities = member.getAuthorityList().stream()
                .map(Function.identity())
                .collect(Collectors.toList());

        String token = jwtTokenProvider.createToken(member.getEmail(), authorities);

        LoginResponseDto.LoginResponse response = LoginResponseDto.LoginResponse.builder()
                .name(member.getName())
                .token(token)
                .build();

        return response;
    }

}