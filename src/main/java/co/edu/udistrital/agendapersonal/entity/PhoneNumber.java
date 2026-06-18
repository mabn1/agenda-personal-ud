package co.edu.udistrital.agendapersonal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "telefono")
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5)
    private String indicativo;

    @Column(nullable = false, length = 20)
    private String numero;

    @ManyToOne
    @JoinColumn(name = "datos_persona_id", nullable = false)
    private PersonalData personalData;
}