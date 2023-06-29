package com.swt.fahrradshop.bestellung.repository;

import com.swt.fahrradshop.bestellung.entity.BestellungEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BestellungRepository extends JpaRepository<BestellungEntity, String> {
    BestellungEntity findByBestellungId(String bestellungId);
}
