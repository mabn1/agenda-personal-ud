package co.edu.udistrital.agendapersonal.service;

import co.edu.udistrital.agendapersonal.dto.request.EmailRequestDTO;
import co.edu.udistrital.agendapersonal.dto.response.EmailResponseDTO;
import co.edu.udistrital.agendapersonal.entity.EmailAddress;
import co.edu.udistrital.agendapersonal.entity.PersonalData;
import co.edu.udistrital.agendapersonal.exception.ResourceNotFoundException;
import co.edu.udistrital.agendapersonal.mapper.ContactMapper;
import co.edu.udistrital.agendapersonal.repository.EmailAddressRepository;
import co.edu.udistrital.agendapersonal.repository.PersonalDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmailAddressService {

    private final EmailAddressRepository emailAddressRepository;
    private final PersonalDataRepository personalDataRepository;
    private final ContactMapper mapper;

    public EmailAddressService(
            EmailAddressRepository emailAddressRepository,
            PersonalDataRepository personalDataRepository,
            ContactMapper mapper) {
        this.emailAddressRepository = emailAddressRepository;
        this.personalDataRepository = personalDataRepository;
        this.mapper = mapper;
    }

    public List<EmailResponseDTO> findByContactId(Long contactoId) {
        if (!personalDataRepository.existsById(contactoId)) {
            throw new ResourceNotFoundException(
                    "Contacto no encontrado con ID: " + contactoId
            );
        }
        return emailAddressRepository.findByPersonalDataId(contactoId).stream()
                .map(mapper::toEmailResponse)
                .collect(Collectors.toList());
    }

    public EmailResponseDTO addEmailToContact(Long contactoId, EmailRequestDTO request) {
        PersonalData personalData = personalDataRepository.findById(contactoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contacto no encontrado con ID: " + contactoId
                ));

        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setEmail(request.getEmail());
        emailAddress.setPersonalData(personalData);

        EmailAddress saved = emailAddressRepository.save(emailAddress);
        return mapper.toEmailResponse(saved);
    }

    public EmailResponseDTO updateEmail(Long contactoId, Long emailId, EmailRequestDTO request) {
        if (!personalDataRepository.existsById(contactoId)) {
            throw new ResourceNotFoundException(
                    "Contacto no encontrado con ID: " + contactoId
            );
        }

        EmailAddress existingEmail = emailAddressRepository.findById(emailId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Email no encontrado con ID: " + emailId
                ));

        if (!existingEmail.getPersonalData().getId().equals(contactoId)) {
            throw new IllegalArgumentException(
                    "El email no pertenece al contacto especificado"
            );
        }

        existingEmail.setEmail(request.getEmail());

        EmailAddress updated = emailAddressRepository.save(existingEmail);
        return mapper.toEmailResponse(updated);
    }

    public void deleteEmail(Long contactoId, Long emailId) {
        if (!personalDataRepository.existsById(contactoId)) {
            throw new ResourceNotFoundException(
                    "Contacto no encontrado con ID: " + contactoId
            );
        }

        EmailAddress emailAddress = emailAddressRepository.findById(emailId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Email no encontrado con ID: " + emailId
                ));

        if (!emailAddress.getPersonalData().getId().equals(contactoId)) {
            throw new IllegalArgumentException(
                    "El email no pertenece al contacto especificado"
            );
        }

        emailAddressRepository.delete(emailAddress);
    }
}