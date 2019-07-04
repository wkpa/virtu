package ru.xkpa.virtu.asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Pavel Kurakin
 */
@Repository
public interface AssetKindRepository extends JpaRepository<AssetKind, Long> {
}
