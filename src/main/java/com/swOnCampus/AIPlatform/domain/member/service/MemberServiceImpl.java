package com.swOnCampus.AIPlatform.domain.member.service;

import com.fasterxml.jackson.databind.JsonNode;
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

    @Value(value = "${spring.api.businessCheck.decodingKey}")
    private String decodingKey;

    @Value(value = "${spring.api.businessCheck.encodingKey}")
    private String encodingKey;

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

        WebClient client = WebClient.builder()
                .baseUrl("https://api.odcloud.kr/api/nts-businessman/v1/status")
                .build();

        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, String>> businessNumbers = new ArrayList<>();
        Map<String, String> businessNumber = new HashMap<>();
        businessNumber.put("b_no", num);
        businessNumbers.add(businessNumber);
        requestBody.put("b_no", businessNumbers);

        String uri = UriComponentsBuilder.fromUriString("")
                .queryParam("serviceKey", encodingKey)
                .build()
                .toUriString();

        JsonNode response = client.post()
                .uri(uri)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .doOnNext(System.out::println)
                .block();

        return (response != null) && (response.path("status_code").asInt() == 200);
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