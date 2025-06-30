package com.unb.fair_management_system.visitor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, UUID> {
  List<Visitor> findByUserId(UUID userId);

  boolean existsByUserIdAndFairId(UUID userId, UUID fairId);

  Optional<Visitor> findByUserIdAndFairId(UUID userId, UUID fairId);
}
