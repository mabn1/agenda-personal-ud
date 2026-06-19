package co.edu.udistrital.agendapersonal.repository;

import co.edu.udistrital.agendapersonal.entity.EmailAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailAddressRepository extends JpaRepository<EmailAddress, Long> {
    List<EmailAddress> findByPersonalDataId(Long personalDataId);
}