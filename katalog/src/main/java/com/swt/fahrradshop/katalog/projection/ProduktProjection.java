package com.swt.fahrradshop.katalog.projection;
import com.swt.fahrradshop.core.events.ProduktReservedEvent;
import com.swt.fahrradshop.core.events.ProduktUnreservedEvent;
import com.swt.fahrradshop.katalog.event.*;
import com.swt.fahrradshop.katalog.entity.Produkt;
import com.swt.fahrradshop.katalog.query.FindProduktQuery;
import com.swt.fahrradshop.katalog.query.FindProdukteQuery;
import com.swt.fahrradshop.katalog.repository.ProduktRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
//This class will match the DB operations for every received event.
public class ProduktProjection {

    private final ProduktRepository produktRepository;

    @EventHandler
    public void on(ProduktCreatedEvent produktCreatedEvent) {
        log.debug("Handling a Produkt creation Command{}", produktCreatedEvent.getProduktId());
        Produkt produkt = new Produkt(
                produktCreatedEvent.getProduktId(),
                produktCreatedEvent.getName(),
                produktCreatedEvent.getPreis(),
                produktCreatedEvent.getAnzahl(),
                produktCreatedEvent.getKategorie(),
                produktCreatedEvent.getVerfuegbarkeit(),
                produktCreatedEvent.getAnzahlToReserve()




        );

        this.produktRepository.save(produkt);


    }

    @EventHandler
    public void on(ProduktUpdatedEvent event) {
     log.debug("Handling a Produkt update Command{}",event.getProduktId());
        Produkt produkt = produktRepository.findById(event.getProduktId()).orElseThrow(()->
                new IllegalArgumentException("Product not found with ID: " + event.getProduktId()));
                produkt.setName(event.getNewName());
                produkt.setPreis(event.getNewPreis());
                produkt.setAnzahl(event.getNewAnzahl());
                produkt.setKategorie(event.getNewKategorie());
                produkt.setVerfuegbarkeit(event.getNewVerfuegbarkeit());
                produkt.setAnzahlToReserve(event.getNewAnzahlToReserve());
      this.produktRepository.save(produkt);
    }
    @EventHandler
    public void on(ProduktDeletedEvent event) {
        log.debug("Handling DeleteProduktEvent for Produkt with ID: {}", event.getProduktId());
        produktRepository.deleteById(event.getProduktId());
    }
    @EventHandler
    public void on(ProduktReservedEvent event) {
        Produkt produktToReserve = produktRepository.findProduktById(event.getProduktId());
        BigDecimal anzahlAvailable = produktToReserve.getAnzahl();
        BigDecimal reservedAnzahl= produktToReserve.getAnzahlToReserve();
        if(anzahlAvailable.compareTo(BigDecimal.valueOf(event.getAnzahlToReserve()))>=0){
            reservedAnzahl =reservedAnzahl.add(BigDecimal.valueOf(event.getAnzahlToReserve()));
            produktToReserve.setAnzahlToReserve(reservedAnzahl);
        }
        produktRepository.save(produktToReserve);
    }
    @EventHandler
    public void on(ProduktUnreservedEvent event){
        Produkt produktToUnreserve = produktRepository.findProduktById(event.getProduktId());
        BigDecimal anzahlAvailable = produktToUnreserve.getAnzahl();
        BigDecimal UnreservedAnzahl= produktToUnreserve.getAnzahlToReserve();

            UnreservedAnzahl =UnreservedAnzahl.subtract(BigDecimal.valueOf(event.getAnzahlToReserve()));
            produktToUnreserve.setAnzahlToReserve(UnreservedAnzahl);

        produktRepository.save(produktToUnreserve);
    }


    @QueryHandler
    public Produkt handle(FindProduktQuery query) {
        log.debug("Handling FindProdukttQuery query: {}", query);
        return this.produktRepository.findById(query.getProduktId()).orElse(null);
    }
    @QueryHandler
    public List<Produkt> handle(FindProdukteQuery query) {
        // Retrieve products from the data source and map them to Produkt instances
        List<Produkt> produktList = produktRepository.findAll();// retrieve and map products
        return produktList;
    }
}
