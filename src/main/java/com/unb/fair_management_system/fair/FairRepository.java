package com.unb.fair_management_system.fair;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FairRepository extends JpaRepository<Fair, UUID> {}
