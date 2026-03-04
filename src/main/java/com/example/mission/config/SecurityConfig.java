package com.example.mission.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * ✅ Spring Security 기본 테이블(users/authorities) 사용
     */
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    /**
     * ✅ 회원가입 시 비밀번호 암호화에 필요 (SignupService가 주입받아 사용)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        /**
         * ✅ H2 콘솔 개발 편의 설정
         */
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        /**
         * ✅ 인가(Authorization) 규칙
         */
        http.authorizeHttpRequests(auth -> auth
                // 공개 페이지
                .requestMatchers("/", "/login", "/signup", "/product/list").permitAll()

                // 정적 리소스
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                // H2 콘솔
                .requestMatchers("/h2-console/**").permitAll()

                // 상품 등록: 로그인한 사람만
                .requestMatchers("/product/add").authenticated()

                /**
                 * ✅ 삭제 권한 정책
                 * - ADMIN: 삭제 가능
                 * - USER: "본인 상품만 삭제"는 컨트롤러/서비스에서 소유자 체크로 막을 거라서
                 *         여기서는 일단 로그인만 요구
                 */
                .requestMatchers("/product/delete/**").authenticated()

                // 회원 관련(예시)
                .requestMatchers("/member/list").hasRole("ADMIN")
                .requestMatchers("/member/detail").hasRole("USER")

                // 나머지는 로그인 필요
                .anyRequest().authenticated()
        );

        /**
         * ✅ 커스텀 로그인 페이지 (Thymeleaf)
         * - GET  /login          : 우리가 만든 로그인 화면 렌더링
         * - POST /login/process  : Spring Security가 로그인 처리
         */
        http.formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
        );

        /**
         * ✅ 로그아웃
         */
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
        );

        return http.build();
    }
}