package com.unb.fair_management_system.authentication.user.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdateUserRequest(
    @Schema(example = "João Silva") @NotBlank String fullName,
    @Schema(example = "joana.silva@email.com") @NotBlank @Email String email,
    @Schema(example = "SenhaForte123!") @NotBlank String password,
    @Schema(example = "[\"SELF\", \"VISITOR\"]") @NotNull List<String> roles,
    @Schema(example = "system") String updatedBy) {}
