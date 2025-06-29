package com.unb.fair_management_system.web;

import com.unb.fair_management_system.authentication.user.create.CreateUserRequest;
import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.create.CreateFairRequest;
import com.unb.fair_management_system.starter.mediator.Mediator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class WebController {

  private final Mediator mediator;

  @GetMapping("/")
  public String home(final Model model) {
    final List<Fair> fairs =
        (List<Fair>)
            mediator
                .handle(
                    new EmptyRequest(), ResolvableType.forClassWithGenerics(List.class, Fair.class))
                .getBody();
    model.addAttribute("fairs", fairs);
    model.addAttribute("contentFragment", "home");
    return "layout";
  }

  // --- User Login and Registration ---
  @GetMapping("/login/user")
  public String userLoginPage(final Authentication authentication, final Model model) {
    if (authentication != null && authentication.isAuthenticated()) {
      return "redirect:/web/fairs";
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
      @ModelAttribute("userRequest") final CreateUserRequest formSubmission) {
    final var safeRequest =
        new CreateUserRequest(
            formSubmission.fullName(),
            formSubmission.email(),
            formSubmission.password(),
            List.of("SELF"),
            "self-registration");
    mediator.handle(safeRequest, UUID.class);
    return "redirect:/login/user";
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
      @ModelAttribute("userRequest") final CreateUserRequest formSubmission) {
    final var safeRequest =
        new CreateUserRequest(
            formSubmission.fullName(),
            formSubmission.email(),
            formSubmission.password(),
            List.of("ADMIN", "ORGANIZER"),
            "admin-registration");
    mediator.handle(safeRequest, UUID.class);
    return "redirect:/login/admin";
  }

  // --- Admin Dashboard ---
  @GetMapping("/admin/dashboard")
  public String adminDashboard(final Model model) {
    model.addAttribute("contentFragment", "admin/dashboard");
    return "layout";
  }

  // --- Fairs Mappings ---
  @GetMapping("/web/fairs")
  public String listFairs(final Model model) {
    final List<Fair> fairs =
        (List<Fair>)
            mediator
                .handle(
                    new EmptyRequest(), ResolvableType.forClassWithGenerics(List.class, Fair.class))
                .getBody();
    model.addAttribute("fairs", fairs);
    model.addAttribute(
        "newFairRequest", new CreateFairRequest(null, null, null, null, null, null, null, null));
    model.addAttribute("contentFragment", "fairs/index");
    return "layout";
  }

  @PostMapping("/web/fairs/create")
  public String createFair(
      @ModelAttribute("newFairRequest") final CreateFairRequest newFairRequest) {
    final var request =
        new CreateFairRequest(
            newFairRequest.name(),
            newFairRequest.description(),
            newFairRequest.startDate(),
            newFairRequest.endDate(),
            newFairRequest.location(),
            newFairRequest.city(),
            newFairRequest.state(),
            "web-interface");
    mediator.handle(request, UUID.class);
    return "redirect:/web/fairs";
  }
}