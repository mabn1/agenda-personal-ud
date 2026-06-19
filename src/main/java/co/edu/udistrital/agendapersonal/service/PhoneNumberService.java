package co.edu.udistrital.agendapersonal.service;

import co.edu.udistrital.agendapersonal.dto.request.PhoneRequestDTO;
import co.edu.udistrital.agendapersonal.dto.response.PhoneResponseDTO;
import co.edu.udistrital.agendapersonal.entity.PersonalData;
import co.edu.udistrital.agendapersonal.entity.PhoneNumber;
import co.edu.udistrital.agendapersonal.exception.ResourceNotFoundException;
import co.edu.udistrital.agendapersonal.mapper.ContactMapper;
import co.edu.udistrital.agendapersonal.repository.PersonalDataRepository;
import co.edu.udistrital.agendapersonal.repository.PhoneNumberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
    private final PersonalDataRepository personalDataRepository;
    private final ContactMapper mapper;

    public PhoneNumberService(
            PhoneNumberRepository phoneNumberRepository,
            PersonalDataRepository personalDataRepository,
            ContactMapper mapper) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.personalDataRepository = personalDataRepository;
        this.mapper = mapper;
    }

    public List<PhoneResponseDTO> findByContactId(Long contactoId) {
        if (!personalDataRepository.existsById(contactoId)) {
            throw new ResourceNotFoundException(
                    "Contacto no encontrado con ID: " + contactoId
            );
        }
        return phoneNumberRepository.findByPersonalDataId(contactoId).stream()
                .map(mapper::toPhoneResponse)
                .collect(Collectors.toList());
    }

    public PhoneResponseDTO addPhoneToContact(Long contactoId, PhoneRequestDTO request) {
        PersonalData personalData = personalDataRepository.findById(contactoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contacto no encontrado con ID: " + contactoId
                ));

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setCountryCode(request.getCountryCode());
        phoneNumber.setNumber(request.getNumber());
        phoneNumber.setPersonalData(personalData);

        PhoneNumber saved = phoneNumberRepository.save(phoneNumber);
        return mapper.toPhoneResponse(saved);
    }

    public PhoneResponseDTO updatePhone(Long contactoId, Long telefonoId, PhoneRequestDTO request) {
        if (!personalDataRepository.existsById(contactoId)) {
            throw new ResourceNotFoundException(
                    "Contacto no encontrado con ID: " + contactoId
            );
        }

        PhoneNumber existingPhone = phoneNumberRepository.findById(telefonoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Teléfono no encontrado con ID: " + telefonoId
                ));

        if (!existingPhone.getPersonalData().getId().equals(contactoId)) {
            throw new IllegalArgumentException(
                    "El teléfono no pertenece al contacto especificado"
            );
        }

        existingPhone.setCountryCode(request.getCountryCode());
        existingPhone.setNumber(request.getNumber());

        PhoneNumber updated = phoneNumberRepository.save(existingPhone);
        return mapper.toPhoneResponse(updated);
    }

    public void deletePhone(Long contactoId, Long telefonoId) {
        if (!personalDataRepository.existsById(contactoId)) {
            throw new ResourceNotFoundException(
                    "Contacto no encontrado con ID: " + contactoId
            );
        }

        PhoneNumber phoneNumber = phoneNumberRepository.findById(telefonoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Teléfono no encontrado con ID: " + telefonoId
                ));

        if (!phoneNumber.getPersonalData().getId().equals(contactoId)) {
            throw new IllegalArgumentException(
                    "El teléfono no pertenece al contacto especificado"
            );
        }

        phoneNumberRepository.delete(phoneNumber);
    }
}