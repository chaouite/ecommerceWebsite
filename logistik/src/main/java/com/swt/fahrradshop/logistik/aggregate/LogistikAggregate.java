package com.swt.fahrradshop.logistik.aggregate;

import com.swt.fahrradshop.core.commands.CreateLogistikCommand;
import com.swt.fahrradshop.core.valueObject.LieferstatusEnum;
import com.swt.fahrradshop.core.commands.CancelLogistikCommand;
import com.swt.fahrradshop.core.commands.SendShippingCommand;
import com.swt.fahrradshop.core.events.LogistikCanceledEvent;
import com.swt.fahrradshop.core.events.LogistikCreatedEvent;
import com.swt.fahrradshop.core.events.ShippingSentEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@Aggregate
@Slf4j
public class LogistikAggregate {

    @AggregateIdentifier
    private String logistikId;

    private String bestellungId;
    private LieferstatusEnum lieferstatusEnum;

    public LogistikAggregate() {
    }

    //triggered when commandGateway saved command
    @CommandHandler
    public LogistikAggregate(CreateLogistikCommand cmd) {
        apply( new LogistikCreatedEvent(cmd.getLogistikId(),
        cmd.getBestellungId(),
        cmd.getLieferstatusEnum()));
    }


    @CommandHandler
    public void handle(CancelLogistikCommand cmd) {

        apply(new LogistikCanceledEvent(cmd.getLogistikId()));
    }

    @CommandHandler
    public void handle(SendShippingCommand cmd){
        if(this.lieferstatusEnum.toString().equals("BEARBEITET"))
        apply(new ShippingSentEvent(
                cmd.getLogistikId(),
                cmd.getBestellungId(),
                LieferstatusEnum.VERSENDET));
        else {
            apply(new ShippingSentEvent(
                    cmd.getLogistikId(),
                    cmd.getBestellungId(),
                    LieferstatusEnum.STORNIERT));
        }
    }

    @EventSourcingHandler
    public void on(LogistikCreatedEvent evt) throws Exception {
        this.logistikId = evt.getLogistikId();
        this.bestellungId = evt.getBestellungId();
        this.lieferstatusEnum = evt.getLieferstatus();
    }

    @EventSourcingHandler
    public void on(LogistikCanceledEvent evt) {
        markDeleted();
    }

    @EventSourcingHandler
    public void on(ShippingSentEvent evt){
        this.logistikId = evt.getLogistikId();
        this.lieferstatusEnum = evt.getLieferstatusEnum();
    }
}
