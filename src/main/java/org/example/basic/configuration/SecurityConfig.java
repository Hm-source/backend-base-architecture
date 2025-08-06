package org.example.basic.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

// SecurityFilterChain 안에서 직접 CORS 설정을 연결함으로써,
// Spring Security의 CORS 필터 (CorsFilter)가 DispatcherServlet 전에 작동
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfigurationSource reactConfigurationSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//      (3) 인증 및 인가 외 모든 종류의 SecurityFilterChain 보안 설정 규칙 적용
        http.cors((cors) -> cors.configurationSource(reactConfigurationSource));
        return http.build();
    }
}

