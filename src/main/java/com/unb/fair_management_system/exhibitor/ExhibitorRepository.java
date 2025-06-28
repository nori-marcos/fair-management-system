package com.unb.fair_management_system.exhibitor;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitorRepository extends JpaRepository<Exhibitor, UUID> {
  List<Exhibitor> findByUserId(UUID userId);
}
