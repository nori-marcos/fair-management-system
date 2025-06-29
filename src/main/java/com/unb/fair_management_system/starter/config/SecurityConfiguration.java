package com.unb.fair_management_system.starter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  private static final String ADMIN_LOGIN_URL = "/login/admin";
  private static final String USER_LOGIN_URL = "/login/user";

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Security configuration for Admin users.
  @Bean
  @Order(1)
  public SecurityFilterChain adminSecurityFilterChain(
      final HttpSecurity http, final UserDetailsService userDetailsService) throws Exception {
    http.securityMatcher("/admin/**", ADMIN_LOGIN_URL)
        .authorizeHttpRequests(
            auth -> auth.requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated())
        .userDetailsService(userDetailsService)
        .formLogin(
            form ->
                form.loginPage(ADMIN_LOGIN_URL)
                    .loginProcessingUrl(ADMIN_LOGIN_URL)
                    .defaultSuccessUrl("/admin/dashboard", true)
                    .failureUrl("/login/admin?error=true"));
    return http.build();
  }

  // Default security configuration for all other users.
  @Bean
  @Order(2)
  public SecurityFilterChain userSecurityFilterChain(
      final HttpSecurity http, final UserDetailsService userDetailsService) throws Exception {
    http.authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/",
                        "/css/**",
                        "/js/**",
                        "/register/**",
                        USER_LOGIN_URL,
                        "/registration-success",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .userDetailsService(userDetailsService)
        .formLogin(
            form ->
                form.loginPage(USER_LOGIN_URL)
                    .loginProcessingUrl(USER_LOGIN_URL)
                    .defaultSuccessUrl("/web/fairs", true)
                    .failureUrl("/login/user?error=true"))
        .logout(
            logout ->
                logout.logoutUrl("/logout").logoutSuccessUrl("/login/user?logout").permitAll());
    return http.build();
  }
}