package co.edu.udistrital.agendapersonal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor  // ← Esta anotación crea el constructor con todos los parámetros
public class PhoneResponseDTO {
    private Long id;
    private String countryCode;
    private String number;
}