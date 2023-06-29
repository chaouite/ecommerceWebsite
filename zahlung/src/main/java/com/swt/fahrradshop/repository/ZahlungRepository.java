package com.swt.fahrradshop.repository;

import com.swt.fahrradshop.entity.ZahlungEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZahlungRepository extends JpaRepository<ZahlungEntity, String> {
    ZahlungEntity findZahlungEntitiesByZahlungId(String zahlungId);
    ZahlungEntity findZahlungByBestellungId(String bestellungId);
}


