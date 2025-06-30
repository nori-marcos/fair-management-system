package com.unb.fair_management_system.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

  @ModelAttribute("loginType")
  public String getLoginType(final HttpSession session) {
    final Object loginType = session.getAttribute("LOGIN_TYPE");
    return loginType != null ? loginType.toString() : "NONE";
  }
}