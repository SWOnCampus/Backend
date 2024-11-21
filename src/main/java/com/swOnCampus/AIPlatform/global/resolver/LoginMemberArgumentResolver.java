package com.swOnCampus.AIPlatform.global.resolver;

import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import com.swOnCampus.AIPlatform.domain.member.repository.MemberRepository;
import com.swOnCampus.AIPlatform.global.annotation.LoginMember;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasParameterAnnotations = parameter.hasParameterAnnotation(LoginMember.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());
        return hasParameterAnnotations && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            throw new IllegalStateException("올바르지 않은 요청 타입입니다. webRequest : " + webRequest);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Member member = null;
        if (authentication.getPrincipal() != "anonymousUser") {
            return memberRepository.findByEmail((String)authentication.getPrincipal())
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
        } else {
            return null;
        }
    }
}
