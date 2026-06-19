package co.edu.udistrital.agendapersonal.repository;

import co.edu.udistrital.agendapersonal.entity.PersonalData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

    Optional<PersonalData> findByDocumentNumber(String documentNumber);

    Page<PersonalData> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);
}