package com.swt.fahrradshop.katalog.repository;
import java.util.UUID;
import com.swt.fahrradshop.katalog.entity.Produkt;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProduktRepository extends JpaRepository<Produkt, String> {

    Produkt findProduktById(String produktId );
}
