package co.edu.udistrital.agendapersonal.controller;

import co.edu.udistrital.agendapersonal.dto.request.ContactRequestDTO;
import co.edu.udistrital.agendapersonal.dto.response.ContactResponseDTO;
import co.edu.udistrital.agendapersonal.service.PersonalDataService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
public class PersonalDataController {

    private final PersonalDataService service;

    public PersonalDataController(PersonalDataService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContactResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<ContactResponseDTO>> listarPaginado(
            @PageableDefault(size = 10, sort = "fullName", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(service.findAllPaginated(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/documento/{documentNumber}")
    public ResponseEntity<ContactResponseDTO> buscarPorDocumento(
            @PathVariable String documentNumber) {
        return ResponseEntity.ok(service.findByDocumentNumber(documentNumber));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<ContactResponseDTO>> buscarPorNombre(
            @RequestParam String nombre,
            @PageableDefault(size = 10, sort = "fullName") Pageable pageable) {
        return ResponseEntity.ok(service.searchByName(nombre, pageable));
    }

    @PostMapping
    public ResponseEntity<ContactResponseDTO> crear(
            @Valid @RequestBody ContactRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ContactRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}