package com.system.Flatform.utils.config;

import com.system.Flatform.utils.security.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig {

//    @Autowired
//    private UserDetailsService customUserDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()// post 방식으로 값을 전송할 때, token 을 사용해야하는 보안 설정 해제
                .authorizeRequests()
                .antMatchers("/", "/login", "/test", "/sign").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER")
                .anyRequest().authenticated(); // 그 외 모든 요청에 대해 인증 필요

//        http.cors().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 정책
//                .and().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .and().formLogin().disable();

        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder 생성
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        // 어떤 UserDetailService 를 사용하고, 어떤 PasswordEncoder를 사용하는지
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//    }


}
