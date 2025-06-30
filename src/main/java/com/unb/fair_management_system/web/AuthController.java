package com.unb.fair_management_system.web;

import com.unb.fair_management_system.authentication.user.create.CreateUserRequest;
import com.unb.fair_management_system.starter.mediator.Mediator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

  private final Mediator mediator;

  // --- User Login and Registration ---
  @GetMapping("/login/user")
  public String userLoginPage(final Authentication authentication, final Model model) {
    if (authentication != null && authentication.isAuthenticated()) {
      return "redirect:/user/dashboard";
    }
    model.addAttribute("contentFragment", "user/login");
    return "layout";
  }

  @GetMapping("/register/user")
  public String userRegisterPage(final Model model) {
    model.addAttribute("userRequest", new CreateUserRequest(null, null, null, null, null));
    model.addAttribute("contentFragment", "user/register");
    return "layout";
  }

  @PostMapping("/register/user")
  public String processUserRegistration(
      @ModelAttribute("userRequest") final CreateUserRequest formSubmission,
      final RedirectAttributes redirectAttributes) {
    try {
      final var safeRequest =
          new CreateUserRequest(
              formSubmission.fullName(),
              formSubmission.email(),
              formSubmission.password(),
              List.of("SELF"),
              "self-registration");
      mediator.handle(safeRequest, UUID.class);

      redirectAttributes.addFlashAttribute(
          "successMessage", "Cadastro realizado com sucesso! Por favor, faça o login.");
      redirectAttributes.addFlashAttribute("username", formSubmission.email());
      return "redirect:/login/user";

    } catch (final IllegalStateException e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
      redirectAttributes.addFlashAttribute("userRequest", formSubmission);
      return "redirect:/register/user";
    }
  }

  // --- Admin Login and Registration ---
  @GetMapping("/login/admin")
  public String adminLoginPage(final Authentication authentication, final Model model) {
    if (authentication != null && authentication.isAuthenticated()) {
      return "redirect:/admin/dashboard";
    }
    model.addAttribute("contentFragment", "admin/login");
    return "layout";
  }

  @GetMapping("/register/admin")
  public String adminRegisterPage(final Model model) {
    model.addAttribute("userRequest", new CreateUserRequest(null, null, null, null, null));
    model.addAttribute("contentFragment", "admin/register");
    return "layout";
  }

  @PostMapping("/register/admin")
  public String processAdminRegistration(
      @ModelAttribute("userRequest") final CreateUserRequest formSubmission,
      final RedirectAttributes redirectAttributes) {
    try {
      final var safeRequest =
          new CreateUserRequest(
              formSubmission.fullName(),
              formSubmission.email(),
              formSubmission.password(),
              List.of("ADMIN", "ORGANIZER"),
              "admin-registration");
      mediator.handle(safeRequest, UUID.class);

      redirectAttributes.addFlashAttribute(
          "successMessage", "Cadastro de admin realizado com sucesso! Por favor, faça o login.");
      redirectAttributes.addFlashAttribute("username", formSubmission.email());
      return "redirect:/login/admin";

    } catch (final IllegalStateException e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
      redirectAttributes.addFlashAttribute("userRequest", formSubmission);
      return "redirect:/register/admin";
    }
  }
}
