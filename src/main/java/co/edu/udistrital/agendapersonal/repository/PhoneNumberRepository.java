package co.edu.udistrital.agendapersonal.repository;

import co.edu.udistrital.agendapersonal.entity.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    List<PhoneNumber> findByPersonalDataId(Long personalDataId);
}