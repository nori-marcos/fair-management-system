package com.unb.fair_management_system.web;

import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.authentication.user.create.CreateUserRequest;
import com.unb.fair_management_system.company.Company;
import com.unb.fair_management_system.company.CompanyRepository;
import com.unb.fair_management_system.company.create.CreateCompanyRequest;
import com.unb.fair_management_system.exhibitor.create.CreateExhibitorRequest;
import com.unb.fair_management_system.exhibitor.fairStatus.ExhibitorFairStatusResponse;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.fair.create.CreateFairRequest;
import com.unb.fair_management_system.product.create.CreateProductRequest;
import com.unb.fair_management_system.starter.mediator.Mediator;
import com.unb.fair_management_system.ticket.TicketResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class WebController {

  private final Mediator mediator;
  private final UserRepository userRepository;
  private final CompanyRepository companyRepository;
  private final FairRepository fairRepository;

  @GetMapping("/")
  public String home(final Model model) {
    final List<Fair> fairs = fairRepository.findAll();
    model.addAttribute("fairs", fairs);
    model.addAttribute("contentFragment", "home");
    return "layout";
  }

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

  // --- User Control ---
  @GetMapping("/user/dashboard")
  public String userDashboard(final Model model) {
    model.addAttribute("contentFragment", "user/dashboard");
    return "layout";
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

  // --- Admin Control ---
  @GetMapping("/admin/dashboard")
  public String adminDashboard(final Model model) {
    model.addAttribute("contentFragment", "admin/dashboard");
    return "layout";
  }

  @GetMapping("/admin/fairs")
  public String listFairs(final Model model) {
    final List<Fair> fairs = fairRepository.findAll();
    model.addAttribute("fairs", fairs);
    model.addAttribute(
        "newFairRequest", new CreateFairRequest(null, null, null, null, null, null, null, null));
    model.addAttribute("contentFragment", "fairs/index");
    return "layout";
  }

  @PostMapping("/admin/fairs/create")
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
    return "redirect:/admin/fairs";
  }

  // --- EXHIBITOR WIZARD ---

  @GetMapping("/exhibit/fairs")
  public String showExhibitParticipationPage(final Model model) {
    final User currentUser =
        getCurrentUser()
            .orElseThrow(() -> new IllegalStateException("Usuário não encontrado na sessão."));
    final UUID userId = currentUser.getId();
    final ExhibitorFairStatusResponse response =
        mediator.handle(userId, ExhibitorFairStatusResponse.class).getBody();

    model.addAttribute("subscribedFairs", response.subscribedFairs());
    model.addAttribute("unsubscribedFairs", response.unsubscribedFairs());
    model.addAttribute("contentFragment", "exhibit/index");
    return "layout";
  }

  @GetMapping("/exhibit/register/company")
  public String showCompanyStep(
      @RequestParam("fairId") final UUID fairId, final HttpSession session, final Model model) {
    session.setAttribute("wizard_fairId", fairId);
    model.addAttribute("companies", companyRepository.findAll());
    model.addAttribute("newCompanyRequest", new CreateCompanyRequest(null, null, null, null, null));
    model.addAttribute("contentFragment", "exhibit/register-company");
    return "layout";
  }

  @PostMapping("/exhibit/register/company")
  public String handleCompanyStep(
      @RequestParam(value = "companyId", required = false) final UUID companyId,
      @ModelAttribute("newCompanyRequest") final CreateCompanyRequest newCompanyRequest,
      final HttpSession session,
      final RedirectAttributes redirectAttributes) {

    UUID companyToUse = companyId;

    if (companyToUse == null) {
      if (newCompanyRequest.name() != null && !newCompanyRequest.name().isBlank()) {
        try {
          final User currentUser =
              getCurrentUser()
                  .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));

          final var safeCompanyRequest =
              new CreateCompanyRequest(
                  newCompanyRequest.name(),
                  newCompanyRequest.email(),
                  newCompanyRequest.phone(),
                  newCompanyRequest.cnpj(),
                  currentUser.getEmail() // Set createdBy with the user's email
                  );

          companyToUse = mediator.handle(safeCompanyRequest, UUID.class).getBody();
        } catch (final Exception e) {
          redirectAttributes.addFlashAttribute(
              "errorMessage", "Falha ao criar empresa: " + e.getMessage());
          redirectAttributes.addAttribute("fairId", session.getAttribute("wizard_fairId"));
          return "redirect:/exhibit/register/company";
        }
      } else {
        redirectAttributes.addFlashAttribute(
            "errorMessage", "Por favor, selecione uma empresa existente ou cadastre uma nova.");
        redirectAttributes.addAttribute("fairId", session.getAttribute("wizard_fairId"));
        return "redirect:/exhibit/register/company";
      }
    }

    session.setAttribute("wizard_companyId", companyToUse);
    return "redirect:/exhibit/register/products";
  }

  @GetMapping("/exhibit/register/products")
  public String showProductsStep(final HttpSession session, final Model model) {
    final UUID companyId = (UUID) session.getAttribute("wizard_companyId");
    if (companyId == null) {
      return "redirect:/exhibit/fairs"; // Protect state
    }
    final Company company = companyRepository.findById(companyId).orElseThrow();
    model.addAttribute("companyName", company.getName());
    model.addAttribute("products", company.getProducts());
    model.addAttribute("newProductRequest", new CreateProductRequest(null, null, null, null, null));
    model.addAttribute("contentFragment", "exhibit/register-products");
    return "layout";
  }

  @PostMapping("/exhibit/register/products")
  public String handleAddProduct(
      @ModelAttribute("newProductRequest") final CreateProductRequest newProductRequest,
      final HttpSession session) {
    final UUID companyId = (UUID) session.getAttribute("wizard_companyId");
    final var productRequestWithCompany =
        new CreateProductRequest(
            newProductRequest.name(),
            newProductRequest.description(),
            newProductRequest.price(),
            companyId,
            "web-exhibit-flow");
    mediator.handle(productRequestWithCompany, UUID.class);
    return "redirect:/exhibit/register/products";
  }

  @GetMapping("/exhibit/register/confirm")
  public String showConfirmStep(final HttpSession session, final Model model) {
    final UUID fairId = (UUID) session.getAttribute("wizard_fairId");
    final UUID companyId = (UUID) session.getAttribute("wizard_companyId");

    if (fairId == null || companyId == null) {
      return "redirect:/exhibit/fairs";
    }

    final Fair fair = fairRepository.findById(fairId).orElseThrow();
    final Company company = companyRepository.findById(companyId).orElseThrow();

    model.addAttribute("fairName", fair.getName());
    model.addAttribute("companyName", company.getName());
    model.addAttribute(
        "exhibitorRequest", new CreateExhibitorRequest(null, null, null, null, null, null));
    model.addAttribute("contentFragment", "exhibit/register-confirm");
    return "layout";
  }

  @PostMapping("/exhibit/register/confirm")
  public String handleConfirmStep(
      @ModelAttribute("exhibitorRequest") final CreateExhibitorRequest exhibitorRequest,
      final HttpSession session) {
    final User currentUser =
        getCurrentUser().orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));

    final var finalRequest =
        new CreateExhibitorRequest(
            exhibitorRequest.contactName(),
            currentUser.getEmail(),
            (UUID) session.getAttribute("wizard_companyId"),
            (UUID) session.getAttribute("wizard_fairId"),
            "web-exhibit-flow",
            currentUser.getId());

    final TicketResponse ticket = mediator.handle(finalRequest, TicketResponse.class).getBody();
    session.removeAttribute("wizard_fairId");
    session.removeAttribute("wizard_companyId");

    return "redirect:/exhibit/ticket/" + ticket.getId();
  }

  @GetMapping("/exhibit/ticket/{ticketId}")
  public String showTicket(@PathVariable final UUID ticketId, final Model model) {
    model.addAttribute("ticketId", ticketId);
    model.addAttribute("contentFragment", "exhibit/ticket-confirmation");
    return "layout";
  }

  // Helper to get current user
  private Optional<User> getCurrentUser() {
    final String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByEmail(username);
  }
}
