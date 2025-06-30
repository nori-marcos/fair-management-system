package com.unb.fair_management_system.starter.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class LoginContextAuthenticationSuccessHandler
    extends SavedRequestAwareAuthenticationSuccessHandler {

  private final String loginType;

  public LoginContextAuthenticationSuccessHandler(final String loginType, final String defaultTargetUrl) {
    this.loginType = loginType;
    this.setDefaultTargetUrl(defaultTargetUrl);
    this.setAlwaysUseDefaultTargetUrl(true);
  }

  @Override
  public void onAuthenticationSuccess(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final Authentication authentication)
      throws ServletException, IOException {
    final HttpSession session = request.getSession();
    session.setAttribute("LOGIN_TYPE", loginType);

    super.onAuthenticationSuccess(request, response, authentication);
  }
}