package jpabook.jpashop.auth;

import jpabook.jpashop.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// security가 /login 요청이 오면 로그인을 진행시킨다.
// 로그인 진행 완료되면 Security Session을 만들어준다.(Security ContextHolder)

// Security Session => Authentication => UserDetails(PrincipalDetails)

@Slf4j
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Member member;
    private Map<String, Object> attributes;

    // 일반 로그인
    public PrincipalDetails(Member member) {
        this.member = member;
    }

    // OAuth 로그인
    public PrincipalDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //ex) 1년 동안 회원이 로그인을 하지 않으면 => 휴면 계정
        //    현재 시간 - 로그인 시간 => 1년을 초과하면 false
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("PrincipalDetails getAuthorities()");
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> member.getRole().name());

        return collect;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return member.getPassword() ;
    }

    @Override
    public String getUsername() {
        return member.getEmail(); // 로그인 식별자
    }

    public Member getMember() {
        return member;
    }

}
