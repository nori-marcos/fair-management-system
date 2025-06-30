package com.unb.fair_management_system.web;

import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.company.Company;
import com.unb.fair_management_system.company.CompanyRepository;
import com.unb.fair_management_system.company.create.CreateCompanyRequest;
import com.unb.fair_management_system.exhibitor.Exhibitor;
import com.unb.fair_management_system.exhibitor.ExhibitorRepository;
import com.unb.fair_management_system.exhibitor.create.CreateExhibitorRequest;
import com.unb.fair_management_system.exhibitor.fairStatus.ExhibitorFairStatusResponse;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.product.create.CreateProductRequest;
import com.unb.fair_management_system.starter.mediator.Mediator;
import com.unb.fair_management_system.ticket.TicketResponse;
import com.unb.fair_management_system.visitor.VisitorRepository;
import com.unb.fair_management_system.visitor.visitorflow.CompanyInFairResponse;
import com.unb.fair_management_system.visitor.visitorflow.FairWithCompaniesResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/exhibit")
@RequiredArgsConstructor
public class ExhibitorWizardController {

  private final Mediator mediator;
  private final UserRepository userRepository;
  private final CompanyRepository companyRepository;
  private final FairRepository fairRepository;
  private final ExhibitorRepository exhibitorRepository;

  @GetMapping("/fairs")
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

  @GetMapping("/fairs/{fairId}")
  public String showFairDetails(@PathVariable UUID fairId, Model model) {
    final Fair fair =
        fairRepository
            .findById(fairId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid fair Id:" + fairId));

    final List<CompanyInFairResponse> companies =
        exhibitorRepository.findByFairId(fair.getId()).stream()
            .map(Exhibitor::getCompany)
            .distinct()
            .map(
                company ->
                    new CompanyInFairResponse(
                        company.getId(), company.getName(), company.getEmail(), company.getPhone()))
            .collect(Collectors.toList());

    model.addAttribute("fair", fair);
    model.addAttribute("companies", companies);
    model.addAttribute("contentFragment", "visit/fair-details");
    return "layout";
  }

  @GetMapping("/register/company")
  public String showCompanyStep(
      @RequestParam("fairId") final UUID fairId, final HttpSession session, final Model model) {
    session.setAttribute("wizard_fairId", fairId);
    model.addAttribute("companies", companyRepository.findAll());
    model.addAttribute("newCompanyRequest", new CreateCompanyRequest(null, null, null, null, null));
    model.addAttribute("contentFragment", "exhibit/register-company");
    return "layout";
  }

  @PostMapping("/register/company")
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
                  currentUser.getEmail());

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

  @GetMapping("/register/products")
  public String showProductsStep(final HttpSession session, final Model model) {
    final UUID companyId = (UUID) session.getAttribute("wizard_companyId");
    if (companyId == null) {
      return "redirect:/exhibit/fairs";
    }
    final Company company = companyRepository.findById(companyId).orElseThrow();
    model.addAttribute("companyName", company.getName());
    model.addAttribute("products", company.getProducts());
    model.addAttribute("newProductRequest", new CreateProductRequest(null, null, null, null, null));
    model.addAttribute("contentFragment", "exhibit/register-products");
    return "layout";
  }

  @PostMapping("/register/products")
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

  @GetMapping("/register/confirm")
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

  @PostMapping("/register/confirm")
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

  @GetMapping("/ticket/{ticketId}")
  public String showTicket(@PathVariable final UUID ticketId, final Model model) {
    model.addAttribute("ticketId", ticketId);
    model.addAttribute("contentFragment", "exhibit/ticket-confirmation");
    return "layout";
  }

  private Optional<User> getCurrentUser() {
    final String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByEmail(username);
  }
}
