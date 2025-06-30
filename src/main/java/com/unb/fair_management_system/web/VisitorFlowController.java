package com.unb.fair_management_system.web;

import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.exhibitor.Exhibitor;
import com.unb.fair_management_system.exhibitor.ExhibitorRepository;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.starter.mediator.Mediator;
import com.unb.fair_management_system.ticket.TicketResponse;
import com.unb.fair_management_system.visitor.Visitor;
import com.unb.fair_management_system.visitor.VisitorRepository;
import com.unb.fair_management_system.visitor.create.CreateVisitorRequest;
import com.unb.fair_management_system.visitor.visitorflow.CompanyInFairResponse;
import com.unb.fair_management_system.visitor.visitorflow.FairWithCompaniesResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/visit")
@RequiredArgsConstructor
public class VisitorFlowController {

  private final Mediator mediator;
  private final UserRepository userRepository;
  private final FairRepository fairRepository;
  private final ExhibitorRepository exhibitorRepository;
  private final VisitorRepository visitorRepository;

  @GetMapping("/fairs")
  public String showVisitorFairsPage(final Model model) {
    final User currentUser =
        getCurrentUser().orElseThrow(() -> new IllegalStateException("User not found in session"));

    final List<FairWithCompaniesResponse> allFairsWithStatus =
        (List<FairWithCompaniesResponse>)
            mediator
                .handle(
                    currentUser.getId(),
                    ResolvableType.forClassWithGenerics(
                        List.class, FairWithCompaniesResponse.class))
                .getBody();

    if (allFairsWithStatus != null) {
      final List<FairWithCompaniesResponse> subscribedFairs =
          allFairsWithStatus.stream()
              .filter(FairWithCompaniesResponse::isSubscribedToFair)
              .toList();

      final List<FairWithCompaniesResponse> unsubscribedFairs =
          allFairsWithStatus.stream()
              .filter(item -> !item.isSubscribedToFair())
              .toList();

      model.addAttribute("subscribedFairs", subscribedFairs);
      model.addAttribute("unsubscribedFairs", unsubscribedFairs);
    } else {
      model.addAttribute("subscribedFairs", Collections.emptyList());
      model.addAttribute("unsubscribedFairs", Collections.emptyList());
    }

    model.addAttribute("contentFragment", "visit/index");
    return "layout";
  }

  @GetMapping("/fairs/{fairId}")
  public String showFairDetails(@PathVariable final UUID fairId, final Model model) {
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

  @PostMapping("/subscribe")
  public String subscribeToFair(
      @RequestParam("fairId") final UUID fairId, final RedirectAttributes redirectAttributes) {
    final User currentUser =
        getCurrentUser().orElseThrow(() -> new IllegalStateException("User not found"));

    final var createVisitorRequest =
        new CreateVisitorRequest(
            currentUser.getFullName(),
            currentUser.getEmail(),
            fairId,
            "visitor-web-flow",
            currentUser.getId());

    try {
      final TicketResponse ticket = mediator.handle(createVisitorRequest, TicketResponse.class).getBody();
      return "redirect:/visit/ticket/" + ticket.getId();
    } catch (final Exception e) {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "Erro ao se inscrever na feira: " + e.getMessage());
      return "redirect:/visit/fairs";
    }
  }

  @GetMapping("/ticket/{ticketId}")
  public String showVisitorTicket(@PathVariable final UUID ticketId, final Model model) {
    model.addAttribute("ticketId", ticketId);
    model.addAttribute("contentFragment", "visit/ticket-confirmation");
    return "layout";
  }

  @PostMapping("/unsubscribe")
  public String unsubscribeFromFair(
      @RequestParam("fairId") final UUID fairId, final RedirectAttributes redirectAttributes) {
    final User currentUser =
        getCurrentUser().orElseThrow(() -> new IllegalStateException("User not found"));

    final Optional<Visitor> visitorOpt =
        visitorRepository.findByUserIdAndFairId(currentUser.getId(), fairId);

    if (visitorOpt.isPresent()) {
      try {
        final UUID visitorId = visitorOpt.get().getId();
        mediator.handle(visitorId, Void.class);
        redirectAttributes.addFlashAttribute("successMessage", "Inscrição cancelada com sucesso!");
      } catch (final Exception e) {
        redirectAttributes.addFlashAttribute(
            "errorMessage", "Erro ao cancelar inscrição: " + e.getMessage());
      }
    } else {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "Inscrição não encontrada para cancelar.");
    }

    return "redirect:/visit/fairs";
  }

  private Optional<User> getCurrentUser() {
    final String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByEmail(username);
  }
}
