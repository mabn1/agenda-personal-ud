package co.edu.udistrital.agendapersonal.mapper;

import co.edu.udistrital.agendapersonal.dto.request.ContactRequestDTO;
import co.edu.udistrital.agendapersonal.dto.response.ContactResponseDTO;
import co.edu.udistrital.agendapersonal.dto.response.EmailResponseDTO;
import co.edu.udistrital.agendapersonal.dto.response.PhoneResponseDTO;
import co.edu.udistrital.agendapersonal.entity.EmailAddress;
import co.edu.udistrital.agendapersonal.entity.PersonalData;
import co.edu.udistrital.agendapersonal.entity.PhoneNumber;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ContactMapper {

    public PersonalData toEntity(ContactRequestDTO request) {
        if (request == null) {
            return null;
        }

        PersonalData entity = new PersonalData();
        entity.setFullName(request.getFullName());
        entity.setDocumentNumber(request.getDocumentNumber());
        entity.setAddress(request.getAddress());


        return entity;
    }

    public ContactResponseDTO toResponse(PersonalData entity) {
        if (entity == null) {
            return null;
        }

        ContactResponseDTO response = new ContactResponseDTO();
        response.setId(entity.getId());
        response.setFullName(entity.getFullName());
        response.setDocumentNumber(entity.getDocumentNumber());
        response.setAddress(entity.getAddress());

        if (entity.getPhoneNumbers() != null) {
            response.setPhones(
                    entity.getPhoneNumbers().stream()
                            .map(this::toPhoneResponse)
                            .collect(Collectors.toList())
            );
        }

        if (entity.getEmailAddresses() != null) {
            response.setEmails(
                    entity.getEmailAddresses().stream()
                            .map(this::toEmailResponse)
                            .collect(Collectors.toList())
            );
        }

        return response;
    }

    public PhoneResponseDTO toPhoneResponse(PhoneNumber phone) {
        if (phone == null) {
            return null;
        }
        return new PhoneResponseDTO(
                phone.getId(),
                phone.getCountryCode(),
                phone.getNumber()
        );
    }

    public EmailResponseDTO toEmailResponse(EmailAddress email) {
        if (email == null) {
            return null;
        }
        return new EmailResponseDTO(
                email.getId(),
                email.getEmail()
        );
    }
}