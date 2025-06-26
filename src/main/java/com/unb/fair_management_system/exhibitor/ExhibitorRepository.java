package com.unb.fair_management_system.exhibitor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExhibitorRepository extends JpaRepository<Exhibitor, UUID> {
}
