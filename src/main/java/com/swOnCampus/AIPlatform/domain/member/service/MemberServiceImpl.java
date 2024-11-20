package com.swOnCampus.AIPlatform.domain.member.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swOnCampus.AIPlatform.domain.member.entity.Authority;
import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import com.swOnCampus.AIPlatform.domain.member.repository.MemberRepository;
import com.swOnCampus.AIPlatform.domain.member.web.dto.*;
import com.swOnCampus.AIPlatform.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.swOnCampus.AIPlatform.domain.member.enums.Authorities.ROLE_ADMIN;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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
        String num = corporation.replace("-", ""); // 사업자 번호에서 '-' 제거

        WebClient client = WebClient.builder()
                .baseUrl("https://api.odcloud.kr/api/nts-businessman/v1/status")
                .build();

        // 요청 Body 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("b_no", List.of(num)); // 사업자 번호 리스트 추가

        // URI 생성
        String uri = UriComponentsBuilder.fromHttpUrl("https://api.odcloud.kr/api/nts-businessman/v1/status")
                .queryParam("serviceKey", decodingKey) // 인증 키 추가
                .toUriString();

        try {
            // API 호출 및 응답 받기 (String으로 받음)
            String responseBody = client.post()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class) // 응답을 String으로 수신
                    .block();

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> response = objectMapper.readValue(responseBody, new TypeReference<>() {});

            System.out.println("Parsed Response: " + response);

            Integer statusCode = (Integer) response.get("status_code");
            return statusCode != null && statusCode == 200;

        } catch (WebClientResponseException e) {
            System.out.println("Error Response: " + e.getResponseBodyAsString());
            e.printStackTrace();
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 회원가입 함수
    @Override
    public SignUpResponseDto.SignUpResponse signUp(SignUpRequestDto.SignupRequest request) {
        Member newMember = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .businessNum(request.getBusinessNum())
                .signUpRoute(request.getSignupRoute())
                .corporation(request.getCorporation())
                .authorityList(new ArrayList<>())
                .build();

        Authority authority = Authority.builder()
                .type(ROLE_ADMIN)
                .build();

        newMember.addRole(authority);

        memberRepository.save(newMember);

        List<Authority> authorities = newMember.getAuthorityList().stream()
                .map(Function.identity())
                .collect(Collectors.toList());

        SignUpResponseDto.SignUpResponse response = SignUpResponseDto.SignUpResponse.builder()
                .name(newMember.getName())
                .build();

        return response;
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