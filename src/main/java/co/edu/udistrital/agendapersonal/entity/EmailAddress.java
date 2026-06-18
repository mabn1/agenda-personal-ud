package co.edu.udistrital.agendapersonal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "correo")
public class EmailAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String email;

    @ManyToOne
    @JoinColumn(name = "datos_persona_id", nullable = false)
    private PersonalData personalData;
}