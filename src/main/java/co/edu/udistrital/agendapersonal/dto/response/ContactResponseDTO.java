package co.edu.udistrital.agendapersonal.dto.response;

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
public class ContactResponseDTO {
    private Long id;
    private String fullName;
    private String documentNumber;
    private String address;
    private List<PhoneResponseDTO> phones = new ArrayList<>();
    private List<EmailResponseDTO> emails = new ArrayList<>();
}