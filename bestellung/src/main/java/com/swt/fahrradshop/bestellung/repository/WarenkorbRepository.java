package com.swt.fahrradshop.bestellung.repository;

import com.swt.fahrradshop.bestellung.entity.WarenkorbEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarenkorbRepository extends JpaRepository<WarenkorbEntity, String> {
    WarenkorbEntity findByWarenkorbId(String warenkorbId);

}
