package com.unb.fair_management_system.starter.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class LoginContextAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final AuthenticationSuccessHandler delegate = new SavedRequestAwareAuthenticationSuccessHandler();
  private final String loginType;
  private final String defaultTargetUrl;

  public LoginContextAuthenticationSuccessHandler(final String loginType, final String defaultTargetUrl) {
    this.loginType = loginType;
    this.defaultTargetUrl = defaultTargetUrl;
  }

  @Override
  public void onAuthenticationSuccess(
      final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication)
      throws IOException, ServletException {
    final HttpSession session = request.getSession();
    session.setAttribute("LOGIN_TYPE", loginType);

    ((SavedRequestAwareAuthenticationSuccessHandler) delegate)
        .setDefaultTargetUrl(defaultTargetUrl);

    delegate.onAuthenticationSuccess(request, response, authentication);
  }
}