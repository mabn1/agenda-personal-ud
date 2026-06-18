package co.edu.udistrital.agendapersonal.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "datos_persona")
public class PersonalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", nullable = false, length = 200)
    private String nombreCompleto;

    @Column(name = "numero_documento", nullable = false, unique = true, length = 20)
    private String numeroDocumento;

    @Column(name = "direccion", nullable = false, length = 300)
    private String direccion;

    @OneToMany(mappedBy = "personalData",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PhoneNumber> telefonos = new ArrayList<>();

    @OneToMany(mappedBy = "personalData",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<EmailAddress> correos = new ArrayList<>();
}