package com.swOnCampus.AIPlatform.domain.mypage.service;

import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import com.swOnCampus.AIPlatform.domain.member.repository.MemberRepository;
import com.swOnCampus.AIPlatform.domain.mypage.web.dto.MyProfileResponseDto;
import com.swOnCampus.AIPlatform.domain.mypage.web.dto.ProfileEditRequestDto;
import com.swOnCampus.AIPlatform.domain.mypage.web.dto.ProfileEditResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {
    private final MemberRepository memberRepository;

    // 마이페이지 프로필 조회
    @Override
    public MyProfileResponseDto.MyProfileResponse getMyProfile(long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if(findMember.isEmpty()){
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }

        Member member = findMember.get();

        MyProfileResponseDto.MyProfileResponse response = MyProfileResponseDto.MyProfileResponse.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .corporationNum(member.getCorporation())
                .reports(new ArrayList<>())
                .build();

        return response;
    }

    // 프로필 수정
    @Override
    public ProfileEditResponseDto.ProfileEditResponse editProfile(long memberId, ProfileEditRequestDto.ProfileEditRequest request) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if(findMember.isEmpty()){
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }

        Member member = findMember.get();
        member.setEmail(request.getEmail());
        member.setName(request.getName());
        member.setPhone(request.getPhone());

        memberRepository.save(member);

        Optional<Member> updateMember = memberRepository.findById(memberId);
        if(updateMember.isEmpty()){
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }

        Member updatedMember = updateMember.get();

        ProfileEditResponseDto.ProfileEditResponse response = ProfileEditResponseDto.ProfileEditResponse.builder()
                .id(memberId)
                .name(updatedMember.getName())
                .email(updatedMember.getEmail())
                .phone(updatedMember.getPhone())
                .build();

        return response;
    }
}
