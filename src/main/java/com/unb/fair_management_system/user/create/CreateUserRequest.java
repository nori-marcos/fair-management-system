package com.unb.fair_management_system.user.create;

import com.unb.fair_management_system.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
    @Schema(example = "João Silva") @NotBlank String fullName,
    @Schema(example = "joana.silva@email.com") @NotBlank @Email String email,
    @Schema(example = "SenhaForte123!") @NotBlank String password,
    @Schema(example = "USER") @NotNull Role role) {}
