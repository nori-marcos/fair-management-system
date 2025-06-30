package com.unb.fair_management_system.web;

import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.fair.create.CreateFairRequest;
import com.unb.fair_management_system.starter.mediator.Mediator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

  private final Mediator mediator;
  private final FairRepository fairRepository;

  @GetMapping("/dashboard")
  public String adminDashboard(final Model model) {
    model.addAttribute("contentFragment", "admin/dashboard");
    return "layout";
  }

  @GetMapping("/fairs")
  public String listFairs(final Model model) {
    final List<Fair> fairs = fairRepository.findAll();
    model.addAttribute("fairs", fairs);
    model.addAttribute(
        "newFairRequest", new CreateFairRequest(null, null, null, null, null, null, null, null));
    model.addAttribute("contentFragment", "fairs/index");
    return "layout";
  }

  @PostMapping("/fairs/create")
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
}
