package co.edu.udistrital.agendapersonal.controller;

import co.edu.udistrital.agendapersonal.dto.request.EmailRequestDTO;
import co.edu.udistrital.agendapersonal.dto.response.EmailResponseDTO;
import co.edu.udistrital.agendapersonal.service.EmailAddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos/{contactoId}/emails")
public class EmailAddressController {

    private final EmailAddressService emailAddressService;

    public EmailAddressController(EmailAddressService emailAddressService) {
        this.emailAddressService = emailAddressService;
    }

    @GetMapping
    public ResponseEntity<List<EmailResponseDTO>> listarEmails(
            @PathVariable Long contactoId) {
        return ResponseEntity.ok(emailAddressService.findByContactId(contactoId));
    }

    @PostMapping
    public ResponseEntity<EmailResponseDTO> agregarEmail(
            @PathVariable Long contactoId,
            @Valid @RequestBody EmailRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(emailAddressService.addEmailToContact(contactoId, request));
    }

    @PutMapping("/{emailId}")
    public ResponseEntity<EmailResponseDTO> actualizarEmail(
            @PathVariable Long contactoId,
            @PathVariable Long emailId,
            @Valid @RequestBody EmailRequestDTO request) {
        return ResponseEntity.ok(
                emailAddressService.updateEmail(contactoId, emailId, request)
        );
    }

    @DeleteMapping("/{emailId}")
    public ResponseEntity<Void> eliminarEmail(
            @PathVariable Long contactoId,
            @PathVariable Long emailId) {
        emailAddressService.deleteEmail(contactoId, emailId);
        return ResponseEntity.noContent().build();
    }
}