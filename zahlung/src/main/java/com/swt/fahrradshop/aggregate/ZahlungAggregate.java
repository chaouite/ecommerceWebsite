package com.swt.fahrradshop.aggregate;

import com.swt.fahrradshop.core.commands.CancelZahlungCommand;
import com.swt.fahrradshop.core.commands.ProcessZahlungCommand;
import com.swt.fahrradshop.core.events.ZahlungCanceledEvent;
import com.swt.fahrradshop.core.events.ZahlungProcessedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import com.swt.fahrradshop.core.valueObject.KreditKarte;
import com.swt.fahrradshop.core.valueObject.ZahlungsstatusEnum;

import java.math.BigDecimal;
import java.util.Random;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@Aggregate
public class ZahlungAggregate {

    @AggregateIdentifier
    private String zahlungId;
    private String bestellungId;
    private BigDecimal gesamtpreis;
    private KreditKarte kreditKarte;
    private ZahlungsstatusEnum zahlungsstatusEnum;

    public ZahlungAggregate() {
    }

    @CommandHandler
    public ZahlungAggregate(ProcessZahlungCommand cmd) {
        //Mock the approval or denial of the payment
        Random random = new Random();
        boolean paymentApproved = random.nextBoolean();

        if (paymentApproved) {
            apply(new ZahlungProcessedEvent(
                    cmd.getZahlungId(),
                    cmd.getBestellungId(),
                    cmd.getGesamtpreis(),
                    cmd.getKreditKarte(),
                    ZahlungsstatusEnum.BEZAHLT
            ));
        } else {
            apply(new ZahlungProcessedEvent(
                    cmd.getZahlungId(),
                    cmd.getBestellungId(),
                    cmd.getGesamtpreis(),
                    cmd.getKreditKarte(),
                    ZahlungsstatusEnum.ABGELEHNT
            ));
        }
    }

    @CommandHandler
    public void handle(CancelZahlungCommand cmd){
        apply(new ZahlungCanceledEvent(cmd.getZahlungId()));
    }

    @EventSourcingHandler
    public void on(ZahlungProcessedEvent evt) {
        this.zahlungId = evt.getZahlungId();
        this.bestellungId = evt.getBestellungId();
        this.gesamtpreis = evt.getGesamtpreis();
        this.kreditKarte = evt.getKreditKarte();
        this.zahlungsstatusEnum = evt.getZahlungsstatusEnum();
    }

    @EventSourcingHandler
    public void on(ZahlungCanceledEvent evt) {
        markDeleted();
    }


}
