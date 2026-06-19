package co.edu.udistrital.agendapersonal.controller;

import co.edu.udistrital.agendapersonal.dto.request.PhoneRequestDTO;
import co.edu.udistrital.agendapersonal.dto.response.PhoneResponseDTO;
import co.edu.udistrital.agendapersonal.service.PhoneNumberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos/{contactoId}/telefonos")
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping
    public ResponseEntity<List<PhoneResponseDTO>> listarTelefonos(
            @PathVariable Long contactoId) {
        return ResponseEntity.ok(phoneNumberService.findByContactId(contactoId));
    }

    @PostMapping
    public ResponseEntity<PhoneResponseDTO> agregarTelefono(
            @PathVariable Long contactoId,
            @Valid @RequestBody PhoneRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(phoneNumberService.addPhoneToContact(contactoId, request));
    }

    @PutMapping("/{telefonoId}")
    public ResponseEntity<PhoneResponseDTO> actualizarTelefono(
            @PathVariable Long contactoId,
            @PathVariable Long telefonoId,
            @Valid @RequestBody PhoneRequestDTO request) {
        return ResponseEntity.ok(
                phoneNumberService.updatePhone(contactoId, telefonoId, request)
        );
    }

    @DeleteMapping("/{telefonoId}")
    public ResponseEntity<Void> eliminarTelefono(
            @PathVariable Long contactoId,
            @PathVariable Long telefonoId) {
        phoneNumberService.deletePhone(contactoId, telefonoId);
        return ResponseEntity.noContent().build();
    }
}