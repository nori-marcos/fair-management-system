package com.unb.fair_management_system.web;

import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.starter.mediator.Mediator;
import com.unb.fair_management_system.ticket.TicketResponse;
import com.unb.fair_management_system.visitor.create.CreateVisitorRequest;
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

  private Optional<User> getCurrentUser() {
    final String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByEmail(username);
  }
}
