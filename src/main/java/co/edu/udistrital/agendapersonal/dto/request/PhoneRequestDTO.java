package co.edu.udistrital.agendapersonal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneRequestDTO {

    @NotBlank(message = "El código de país es obligatorio")
    @Pattern(
            regexp = "^\\+[0-9]{1,4}$",
            message = "Formato de código de país inválido. Ejemplo: +57"
    )
    private String countryCode;

    @NotBlank(message = "El número de teléfono es obligatorio")
    @Size(min = 7, max = 20, message = "El número debe tener entre 7 y 20 dígitos")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "El número de teléfono solo debe contener dígitos"
    )
    private String number;
}