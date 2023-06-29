package com.swt.fahrradshop.bestellung.aggregate;

import com.swt.fahrradshop.bestellung.command.*;
import com.swt.fahrradshop.bestellung.event.*;
import com.swt.fahrradshop.bestellung.valueObject.WarenkorbProdukt;
import com.swt.fahrradshop.bestellung.valueObject.WarenkorbStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

@Slf4j
@Aggregate
public class WarenkorbAggregate {

    @AggregateIdentifier
    private String warenkorbId;

    private String kundeId;
    private List<WarenkorbProdukt> produkteList;
    private WarenkorbStatusEnum warenkorbStatus;

    public WarenkorbAggregate() {
    }

    @CommandHandler
    public WarenkorbAggregate(CreateWarenkorbCommand cmd) {
        WarenkorbCreatedEvent evt = WarenkorbCreatedEvent.builder()
                .warenkorbId(cmd.getWarenkorbId())
                .KundeId(cmd.getKundeId())
                .produkte(cmd.getProdukte())
                .warenkorbStatus(cmd.getWarenkorbStatus())
                .build();
        AggregateLifecycle.apply(evt);
    }

    @CommandHandler
    public void handle(AddProduktToWarenkorbCommand cmd) {
        ProduktToWarenkorbAddedEvent evt = new ProduktToWarenkorbAddedEvent(cmd.getWarenkorbId(), cmd.getProduktId(), cmd.getAnzahl());
        AggregateLifecycle.apply(evt);
    }

    @CommandHandler
    public void handle(DeleteProduktFromWarenkorbCommand cmd) {
        ProduktFromWarenkorbDeletedEvent evt = new ProduktFromWarenkorbDeletedEvent(cmd.getWarenkorbId(), cmd.getProduktId());
        AggregateLifecycle.apply(evt);
    }

    @CommandHandler
    public void handle(OrderWarenkorbCommand cmd) {
        WarenkorbOrderedEvent evt = new WarenkorbOrderedEvent(cmd.getWarenkorbId());
        AggregateLifecycle.apply(evt);
    }

    @CommandHandler
    public void handle(UnorderWarenkorbCommand cmd) {
        WarenkorbUnorderedEvent evt = new WarenkorbUnorderedEvent(cmd.getWarenkorbId());
        AggregateLifecycle.apply(evt);
    }
    @EventSourcingHandler
    private void on(WarenkorbCreatedEvent evt) {
        this.warenkorbId = evt.getWarenkorbId();
        this.kundeId = evt.getKundeId();
        this.produkteList = evt.getProdukte();
        this.warenkorbStatus = evt.getWarenkorbStatus();
    }

    @EventSourcingHandler
    private void on(ProduktToWarenkorbAddedEvent evt) {
        this.warenkorbId = evt.getWarenkorbId();
    }

    @EventSourcingHandler
    private void on(ProduktFromWarenkorbDeletedEvent evt) {
        this.warenkorbId = evt.getWarenkorbId();
    }

    @EventSourcingHandler
    private void on(WarenkorbOrderedEvent evt) {
        this.warenkorbId = evt.getWarenkorbId();
    }
    @EventSourcingHandler
    private void on(WarenkorbUnorderedEvent evt) {
        this.warenkorbId = evt.getWarenkorbId();
    }

}
