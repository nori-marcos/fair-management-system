package com.unb.fair_management_system.web;

import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.FairRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final FairRepository fairRepository;

  @GetMapping("/")
  public String home(final Model model) {
    final List<Fair> fairs = fairRepository.findAll();
    model.addAttribute("fairs", fairs);
    model.addAttribute("contentFragment", "home");
    return "layout";
  }

  @GetMapping("/user/dashboard")
  public String userDashboard(final Model model) {
    model.addAttribute("contentFragment", "user/dashboard");
    return "layout";
  }
}
