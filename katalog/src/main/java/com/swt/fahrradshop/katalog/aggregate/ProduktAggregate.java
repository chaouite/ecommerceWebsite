package com.swt.fahrradshop.katalog.aggregate;

import com.swt.fahrradshop.core.commands.ReserveProduktCommand;
import com.swt.fahrradshop.core.commands.UnreserveProduktCommand;
import com.swt.fahrradshop.core.events.ProduktReservedEvent;
import com.swt.fahrradshop.core.events.ProduktUnreservedEvent;
import com.swt.fahrradshop.katalog.command.*;
import com.swt.fahrradshop.katalog.event.*;
import com.swt.fahrradshop.katalog.valueObject.Verfuegbarkeit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import com.swt.fahrradshop.katalog.valueObject.Kategorie;
import java.math.BigDecimal;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Aggregate
//this aggregate receives and handles the Commands and for every Command will dispatch a Query.
public class ProduktAggregate {
    @AggregateIdentifier
    private String produktId;
    private String Name;
    private BigDecimal Preis;
    private BigDecimal Anzahl;
    private Kategorie Kategorie;
    private Verfuegbarkeit Verfuegbarkeit;
    private BigDecimal AnzahlToReserve;


    @CommandHandler
    public ProduktAggregate(CreateProduktCommand command){
        // this is an Axon annotation used to notify the ProduktAggregate that a new Produkt was creating by publishing a ProduktCreatedEvent
        AggregateLifecycle.apply(
                new ProduktCreatedEvent(
                        command.getProduktId(),
                        command.getName(),
                        command.getPreis(),
                        command.getAnzahl(),
                        command.getKategorie(),
                        command.getVerfuegbarkeit(),
                        command.getAnzahlToReserve()

                )
        );

    }
    @CommandHandler
    public void handle(UpdateProduktCommand command){

                      //this is an Axon annotation used to apply the event to the ProduktAggregate
                      AggregateLifecycle.apply(new ProduktUpdatedEvent(
                              command.getProduktId(),
                              command.getNewName(),
                              command.getNewPreis(),
                              command.getNewAnzahl(),
                              command.getNewKategorie(),
                              command.getNewVerfuegbarkeit(),
                              command.getNewAnzahlToReserve()));

    }

    @CommandHandler
    public void handle(DeleteProduktCommand command) {
        AggregateLifecycle.apply(new ProduktDeletedEvent(command.getProduktId()));
    }
   @CommandHandler
   public void handle(ReserveProduktCommand command) {
       apply(new ProduktReservedEvent(command.getProduktId(),command.getAnzahlToReserve()));}

       @CommandHandler
       public void handle(UnreserveProduktCommand command){
           apply (new ProduktUnreservedEvent(command.getProduktId(),command.getAnzahlToReserve()));

       }

@EventSourcingHandler
    public void on(ProduktCreatedEvent event){

        this.produktId = event.getProduktId();
        this.Name = event.getName();
        this.Preis = event.getPreis();
        this.Anzahl=event.getAnzahl();
        this.Kategorie=event.getKategorie();
        this.Verfuegbarkeit=event.getVerfuegbarkeit();
        this.AnzahlToReserve=event.getAnzahlToReserve();

    }
    @EventSourcingHandler
    public void on(ProduktUpdatedEvent event){

        this.produktId = event.getProduktId();
        this.Name = event.getNewName();
        this.Preis= event.getNewPreis();
        this.Anzahl=event.getNewAnzahl();
        this.Kategorie=event.getNewKategorie();
        this.Verfuegbarkeit=event.getNewVerfuegbarkeit();
        this.AnzahlToReserve=event.getNewAnzahl();
    }
    @EventSourcingHandler
    public void on(ProduktDeletedEvent event) {
       markDeleted();
    }
   @EventSourcingHandler
    public void on(ProduktReservedEvent event) {
        this.produktId=event.getProduktId();
        this.AnzahlToReserve=BigDecimal.valueOf(event.getAnzahlToReserve());

      }
      @EventSourcingHandler
public void on (ProduktUnreservedEvent event){
    this.produktId=event.getProduktId();

      }

    }




