package com.femcoders.tico.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActivationRequest(
        @NotBlank(message = "El email es obligatorio")
        String email,
        @NotBlank(message = "El código es obligatorio") 
        @Size(min = 6, max = 6, message = "El código debe tener exactamente 6 caracteres")
        String code,
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,
        @NotBlank(message = "La confirmación de contraseña es obligatoria")
        String confirmPassword) {
}
