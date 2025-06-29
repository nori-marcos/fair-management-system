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
  public String home(Model model) {
    // Fetch the list of fairs to display on the home page
    List<Fair> fairs = (List<Fair>) mediator.handle(
        new EmptyRequest(), ResolvableType.forClassWithGenerics(List.class, Fair.class)
    ).getBody();
    model.addAttribute("fairs", fairs);

    // Point to the home content fragment
    model.addAttribute("contentFragment", "content/home");
    return "layout";
  }

  // --- User Login and Registration ---
  @GetMapping("/login/user")
  public String userLoginPage(Model model) {
    model.addAttribute("contentFragment", "content/login-user");
    return "layout";
  }

  @GetMapping("/register/user")
  public String userRegisterPage(Model model) {
    model.addAttribute("userRequest", new CreateUserRequest(null, null, null, null, null));
    model.addAttribute("contentFragment", "content/register-user");
    return "layout";
  }

  @PostMapping("/register/user")
  public String processUserRegistration(@ModelAttribute("userRequest") CreateUserRequest formSubmission) {
    var safeRequest = new CreateUserRequest(
        formSubmission.fullName(),
        formSubmission.email(),
        formSubmission.password(),
        List.of("SELF"),
        "self-registration"
    );
    mediator.handle(safeRequest, UUID.class);
    return "redirect:/login/user";
  }

  // --- Admin Login and Registration ---
  @GetMapping("/login/admin")
  public String adminLoginPage(Model model) {
    model.addAttribute("contentFragment", "content/login-admin");
    return "layout";
  }

  @GetMapping("/register/admin")
  public String adminRegisterPage(Model model) {
    model.addAttribute("userRequest", new CreateUserRequest(null, null, null, null, null));
    model.addAttribute("contentFragment", "content/register-admin");
    return "layout";
  }

  @PostMapping("/register/admin")
  public String processAdminRegistration(@ModelAttribute("userRequest") CreateUserRequest formSubmission) {
    var safeRequest = new CreateUserRequest(
        formSubmission.fullName(),
        formSubmission.email(),
        formSubmission.password(),
        List.of("ADMIN", "ORGANIZER"), // Assign Admin and Organizer roles
        "admin-registration"
    );
    mediator.handle(safeRequest, UUID.class);
    return "redirect:/login/admin";
  }

  // --- Admin Dashboard ---
  @GetMapping("/admin/dashboard")
  public String adminDashboard(Model model) {
    model.addAttribute("contentFragment", "content/admin-dashboard");
    return "layout";
  }

  // --- Fairs Mappings ---
  @GetMapping("/web/fairs")
  public String listFairs(Model model) {
    List<Fair> fairs = (List<Fair>) mediator.handle(
        new EmptyRequest(), ResolvableType.forClassWithGenerics(List.class, Fair.class)
    ).getBody();
    model.addAttribute("fairs", fairs);
    model.addAttribute("newFairRequest", new CreateFairRequest(null, null, null, null, null, null, null, null));
    model.addAttribute("contentFragment", "content/fairs");
    return "layout";
  }

  @PostMapping("/web/fairs/create")
  public String createFair(@ModelAttribute("newFairRequest") CreateFairRequest newFairRequest) {
    var request =
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