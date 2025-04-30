package jpabook.jpashop.oauth;

import jpabook.jpashop.auth.PrincipalDetails;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Role;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    // 구글로부터 받은 userRequest 데이터에 대한 후처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //userRequest 정보 -> loadUser 호출 -> 구글로부터 회원 프로필 받아옴
        //userRequest: 구글 로그인 클릭 -> 구글 로그인 화면 -> 로그인 완료 -> code를 리턴(OAuth-Client 라이브러리) -> AccessToken 요청
        //loadUser: 회원 프로필 받아야함

        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;

        String provider = userRequest.getClientRegistration().getRegistrationId();

        if ("google".equals(provider)) {
            log.info("google login");
            oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());
        }
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String loginId = provider + "_" + providerId;
        String name = oAuth2UserInfo.getName();

        Member member;
        try {
            Member findMember = memberRepository.findByEmail(loginId);
            member = findMember;

        } catch (Exception e) {

            // 첫 로그인이면 회원 등록
            member = new Member();
            member.setName(name);
            member.setEmail(email); // provider_providerId or email?
            member.setProvider(provider);
            member.setProviderId(providerId);
            member.setRole(Role.USER);

            memberService.join(member);
        }

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }

}
