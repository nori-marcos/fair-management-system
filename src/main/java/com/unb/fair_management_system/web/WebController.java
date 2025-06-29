package com.unb.fair_management_system.web;

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

  @GetMapping("/web/fairs")
  public String listFairs(Model model) {
    // 1. Load the data for the page
    List<Fair> fairs =
        (List<Fair>)
            mediator
                .handle(
                    new EmptyRequest(), ResolvableType.forClassWithGenerics(List.class, Fair.class))
                .getBody();
    model.addAttribute("fairs", fairs);
    model.addAttribute(
        "newFairRequest", new CreateFairRequest(null, null, null, null, null, null, null, null));

    // 2. We comment this line out for the test
     model.addAttribute("contentFragment", "fairs :: content");

    // 3. Return the main layout template
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

  // You can add other pages using the same pattern
  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("contentFragment", "home :: content");
    return "layout";
  }
}
