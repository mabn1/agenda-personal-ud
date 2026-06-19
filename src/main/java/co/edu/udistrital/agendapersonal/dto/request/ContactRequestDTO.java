package co.edu.udistrital.agendapersonal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequestDTO {

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String fullName;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(max = 20, message = "El documento no puede exceder 20 caracteres")
    private String documentNumber;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 300, message = "La dirección no puede exceder 300 caracteres")
    private String address;

    private List<PhoneRequestDTO> phones = new ArrayList<>();
    private List<EmailRequestDTO> emails = new ArrayList<>();
}