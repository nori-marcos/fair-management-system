package com.unb.fair_management_system.starter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Security configuration for Admin users.
   */
  @Bean
  @Order(1)
  public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/admin/**", "/login/admin")
        .authorizeHttpRequests(auth -> auth
                                           .requestMatchers("/admin/**").hasRole("ADMIN")
                                           .anyRequest().authenticated()
        )
        .formLogin(form -> form
                               .loginPage("/login/admin")
                               .loginProcessingUrl("/login/admin")
                               .defaultSuccessUrl("/admin/dashboard", true)
                               .failureUrl("/login/admin?error=true")
        );
    return http.build();
  }

  /**
   * Default security configuration for all other users.
   */
  @Bean
  @Order(2)
  public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
                                           .requestMatchers("/", "/css/**", "/js/**", "/register/**", "/login/user", "/registration-success").permitAll()
                                           .anyRequest().authenticated()
        )
        .formLogin(form -> form
                               .loginPage("/login/user")
                               .loginProcessingUrl("/login/user")
                               .defaultSuccessUrl("/web/fairs", true)
                               .failureUrl("/login/user?error=true")
        )
        .logout(logout -> logout
                              .logoutUrl("/logout")
                              .logoutSuccessUrl("/login/user?logout")
                              .permitAll()
        );
    return http.build();
  }
}