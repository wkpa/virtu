package ru.xkpa.virtu.agreement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Pavel Kurakin
 */
@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {
}
