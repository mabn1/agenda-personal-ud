package co.edu.udistrital.agendapersonal.service;

import co.edu.udistrital.agendapersonal.dto.request.ContactRequestDTO;
import co.edu.udistrital.agendapersonal.dto.response.ContactResponseDTO;
import co.edu.udistrital.agendapersonal.entity.EmailAddress;
import co.edu.udistrital.agendapersonal.entity.PersonalData;
import co.edu.udistrital.agendapersonal.entity.PhoneNumber;
import co.edu.udistrital.agendapersonal.exception.DuplicateDocumentException;
import co.edu.udistrital.agendapersonal.exception.ResourceNotFoundException;
import co.edu.udistrital.agendapersonal.mapper.ContactMapper;
import co.edu.udistrital.agendapersonal.repository.PersonalDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonalDataService {

    private final PersonalDataRepository repository;
    private final ContactMapper mapper;

    public PersonalDataService(PersonalDataRepository repository, ContactMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ContactResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public Page<ContactResponseDTO> findAllPaginated(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    public Page<ContactResponseDTO> searchByName(String name, Pageable pageable) {
        return repository.findByFullNameContainingIgnoreCase(name, pageable)
                .map(mapper::toResponse);
    }

    public ContactResponseDTO findById(Long id) {
        PersonalData entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Persona no encontrada con ID: " + id
                ));
        return mapper.toResponse(entity);
    }

    public ContactResponseDTO findByDocumentNumber(String documentNumber) {
        PersonalData entity = repository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Persona no encontrada con documento: " + documentNumber
                ));
        return mapper.toResponse(entity);
    }

    public ContactResponseDTO save(ContactRequestDTO request) {
        repository.findByDocumentNumber(request.getDocumentNumber())
                .ifPresent(existing -> {
                    throw new DuplicateDocumentException(
                            "Ya existe una persona con el documento: " +
                                    request.getDocumentNumber()
                    );
                });

        PersonalData entity = mapper.toEntity(request);

        if (request.getPhones() != null) {
            request.getPhones().forEach(phoneRequest -> {
                PhoneNumber phone = new PhoneNumber();
                phone.setCountryCode(phoneRequest.getCountryCode());
                phone.setNumber(phoneRequest.getNumber());
                phone.setPersonalData(entity);
                entity.getPhoneNumbers().add(phone);
            });
        }

        if (request.getEmails() != null) {
            request.getEmails().forEach(emailRequest -> {
                EmailAddress email = new EmailAddress();
                email.setEmail(emailRequest.getEmail());
                email.setPersonalData(entity);
                entity.getEmailAddresses().add(email);
            });
        }

        PersonalData saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    public ContactResponseDTO update(Long id, ContactRequestDTO request) {
        PersonalData existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contacto no encontrado con ID: " + id
                ));

        existing.setFullName(request.getFullName());
        existing.setAddress(request.getAddress());

        existing.getPhoneNumbers().clear();
        if (request.getPhones() != null) {
            request.getPhones().forEach(phoneRequest -> {
                PhoneNumber phone = new PhoneNumber();
                phone.setCountryCode(phoneRequest.getCountryCode());
                phone.setNumber(phoneRequest.getNumber());
                phone.setPersonalData(existing);
                existing.getPhoneNumbers().add(phone);
            });
        }

        existing.getEmailAddresses().clear();
        if (request.getEmails() != null) {
            request.getEmails().forEach(emailRequest -> {
                EmailAddress email = new EmailAddress();
                email.setEmail(emailRequest.getEmail());
                email.setPersonalData(existing);
                existing.getEmailAddresses().add(email);
            });
        }

        PersonalData updated = repository.save(existing);
        return mapper.toResponse(updated);
    }

    public void delete(Long id) {
        PersonalData entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contacto no encontrado con ID: " + id
                ));
        repository.delete(entity);
    }
}