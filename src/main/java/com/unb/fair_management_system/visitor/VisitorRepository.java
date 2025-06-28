package com.unb.fair_management_system.visitor;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, UUID> {
  List<Visitor> findByUserId(UUID userId);
}
