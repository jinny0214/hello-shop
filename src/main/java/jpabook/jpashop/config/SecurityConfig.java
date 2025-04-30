package jpabook.jpashop.config;

import jpabook.jpashop.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
// securedEnabled: Secured annotation 활성화, prePostEnabled: preAuthorize 어노테이션 활성화
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable);// http.csrf(csrfConfigurer -> csrfConfigurer.disable())

        // 폼 로그인 방식 설정
        http.formLogin(form -> form
                        .loginPage("/loginForm") // 로그인 화면 URL (get)
                        .usernameParameter("email") // 기본 파라미터 username -> email로 설정 해주기
                        .loginProcessingUrl("/login") // 로그인 요청 처리 URL (post)
                        .defaultSuccessUrl("/home") // 로그인 성공시 리다이렉트 URL
                        .permitAll()); // loginPage, loginProcessingUrl는 모두 접근 허용
        // 접근 권한 설정
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**", "/loginForm","/home").permitAll() // 누구나 접근 가능
                        .requestMatchers("/admin/**").hasRole("ADMIN") // ADMIN만 접근 가능
                        .requestMatchers("/user/**").hasRole("USER") // USER만 접근 가능
                        //.anyRequest().authenticated()); // 나머지 요청은 인증 필요
                        .anyRequest().permitAll());
        // OAuth 2.0 로그인 방식 설정
        http.oauth2Login(oauth2 -> oauth2
                        .loginPage("/loginForm")
                        .defaultSuccessUrl("/home") // 로그인 성공시 리다이렉트 URL
                        .failureUrl("/oauth2/authorization/google")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                            .userService(principalOauth2UserService)));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
