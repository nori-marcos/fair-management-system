package com.unb.fair_management_system.web;

import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.authentication.user.delete.DeleteUserHandler;
import com.unb.fair_management_system.company.CompanyRepository;
import com.unb.fair_management_system.company.create.CreateCompanyRequest;
import com.unb.fair_management_system.company.delete.DeleteCompanyHandler;
import com.unb.fair_management_system.exhibitor.ExhibitorRepository;
import com.unb.fair_management_system.exhibitor.delete.DeleteExhibitorHandler;
import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.fair.create.CreateFairRequest;
import com.unb.fair_management_system.fair.delete.DeleteFairHandler;
import com.unb.fair_management_system.product.ProductRepository;
import com.unb.fair_management_system.product.delete.DeleteProductHandler;
import com.unb.fair_management_system.starter.mediator.Mediator;
import com.unb.fair_management_system.visitor.VisitorRepository;
import com.unb.fair_management_system.visitor.delete.DeleteVisitorHandler;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

  private final Mediator mediator;
  private final FairRepository fairRepository;
  private final CompanyRepository companyRepository;
  private final ProductRepository productRepository;
  private final VisitorRepository visitorRepository;
  private final ExhibitorRepository exhibitorRepository;
  private final DeleteExhibitorHandler deleteExhibitorHandler;
  private final DeleteFairHandler deleteFairHandler;
  private final DeleteCompanyHandler deleteCompanyHandler;
  private final DeleteProductHandler deleteProductHandler;
  private final DeleteVisitorHandler deleteVisitorHandler;
  private final DeleteUserHandler deleteUserHandler;
  private final UserRepository userRepository;

  @GetMapping("/dashboard")
  public String adminDashboard(final Model model) {
    model.addAttribute("contentFragment", "admin/dashboard");
    return "layout";
  }

  // --- Fair Management ---
  @GetMapping("/fairs")
  public String manageFairs(final Model model) {
    model.addAttribute("fairs", fairRepository.findAll());
    model.addAttribute(
        "newFairRequest",
        new CreateFairRequest(null, null, null, null, null, null, null, "admin-interface"));
    model.addAttribute("contentFragment", "admin/fairs");
    return "layout";
  }

  @PostMapping("/fairs/create")
  public String createFair(
      @ModelAttribute("newFairRequest") final CreateFairRequest newFairRequest,
      final RedirectAttributes redirectAttributes) {
    try {
      mediator.handle(newFairRequest, UUID.class);
      redirectAttributes.addFlashAttribute("successMessage", "Feira criada com sucesso.");
    } catch (final Exception e) {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "Erro ao criar feira: " + e.getMessage());
    }
    return "redirect:/admin/fairs";
  }

  @PostMapping("/fairs/delete/{id}")
  public String deleteFair(
      @PathVariable final UUID id, final RedirectAttributes redirectAttributes) {
    try {
      deleteFairHandler.handle(id);
      redirectAttributes.addFlashAttribute("successMessage", "Feira deletada com sucesso.");
    } catch (final Exception e) {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "Erro ao deletar feira: " + e.getMessage());
    }
    return "redirect:/admin/fairs";
  }

  // --- Company Management ---
  @GetMapping("/companies")
  public String manageCompanies(final Model model) {
    model.addAttribute("companies", companyRepository.findAll());
    model.addAttribute(
        "newCompanyRequest", new CreateCompanyRequest(null, null, null, null, "admin-interface"));
    model.addAttribute("contentFragment", "admin/companies");
    return "layout";
  }

  @PostMapping("/companies/create")
  public String createCompany(
      @ModelAttribute("newCompanyRequest") final CreateCompanyRequest newCompanyRequest,
      final RedirectAttributes redirectAttributes) {
    try {
      mediator.handle(newCompanyRequest, UUID.class);
      redirectAttributes.addFlashAttribute("successMessage", "Empresa criada com sucesso.");
    } catch (final Exception e) {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "Erro ao criar empresa: " + e.getMessage());
    }
    return "redirect:/admin/companies";
  }

  @PostMapping("/companies/delete/{id}")
  public String deleteCompany(
      @PathVariable final UUID id, final RedirectAttributes redirectAttributes) {
    try {
      deleteCompanyHandler.handle(id);
      redirectAttributes.addFlashAttribute("successMessage", "Empresa deletada com sucesso.");
    } catch (final Exception e) {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "Erro ao deletar empresa: " + e.getMessage());
    }
    return "redirect:/admin/companies";
  }

  // --- Product Management ---
  @GetMapping("/products")
  public String manageProducts(final Model model) {
    model.addAttribute("products", productRepository.findAll());
    model.addAttribute("contentFragment", "admin/products");
    return "layout";
  }

  @PostMapping("/products/delete/{id}")
  public String deleteProduct(
      @PathVariable final UUID id, final RedirectAttributes redirectAttributes) {
    try {
      deleteProductHandler.handle(id);
      redirectAttributes.addFlashAttribute("successMessage", "Produto deletado com sucesso.");
    } catch (final Exception e) {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "Erro ao deletar produto: " + e.getMessage());
    }
    return "redirect:/admin/products";
  }

  // --- Visitor Management ---
  @GetMapping("/visitors")
  public String manageVisitors(final Model model) {
    model.addAttribute("visitors", visitorRepository.findAll());
    model.addAttribute("contentFragment", "admin/visitors");
    return "layout";
  }

  @PostMapping("/visitors/delete/{id}")
  public String deleteVisitor(
      @PathVariable final UUID id, final RedirectAttributes redirectAttributes) {
    try {
      deleteVisitorHandler.handle(id);
      redirectAttributes.addFlashAttribute("successMessage", "Visitante deletado com sucesso.");
    } catch (final Exception e) {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "Erro ao deletar visitante: " + e.getMessage());
    }
    return "redirect:/admin/visitors";
  }

  // --- Exhibitor Management ---
  @GetMapping("/exhibitors")
  public String manageExhibitors(final Model model) {
    model.addAttribute("exhibitors", exhibitorRepository.findAll());
    model.addAttribute("contentFragment", "admin/exhibitors");
    return "layout";
  }

  @PostMapping("/exhibitors/delete/{id}")
  public String deleteExhibitor(
      @PathVariable final UUID id, final RedirectAttributes redirectAttributes) {
    try {
      deleteExhibitorHandler.handle(id);
      redirectAttributes.addFlashAttribute("successMessage", "Expositor deletado com sucesso.");
    } catch (final Exception e) {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "Erro ao deletar expositor: " + e.getMessage());
    }
    return "redirect:/admin/exhibitors";
  }

  // --- User Management ---
  @GetMapping("/users")
  public String manageUsers(final Model model) {
    model.addAttribute("users", userRepository.findAll());
    model.addAttribute("contentFragment", "admin/users");
    return "layout";
  }

  @PostMapping("/users/delete/{id}")
  public String deleteUser(
      @PathVariable final UUID id, final RedirectAttributes redirectAttributes) {
    try {
      deleteUserHandler.handle(id);
      redirectAttributes.addFlashAttribute("successMessage", "Usuário deletado com sucesso.");
    } catch (final Exception e) {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "Erro ao deletar usuário: " + e.getMessage());
    }
    return "redirect:/admin/users";
  }
}
