package com.swt.fahrradshop.bestellung.saga;

import com.swt.fahrradshop.bestellung.command.CancelBestellungCommand;
import com.swt.fahrradshop.bestellung.command.CreateBestellungCommand;
import com.swt.fahrradshop.bestellung.command.UnorderWarenkorbCommand;
import com.swt.fahrradshop.bestellung.command.UpdatePayedOrSentBestellungCommand;
import com.swt.fahrradshop.bestellung.event.BestellungCanceledEvent;
import com.swt.fahrradshop.bestellung.event.BestellungCreatedEvent;
import com.swt.fahrradshop.bestellung.event.WarenkorbOrderedEvent;
import com.swt.fahrradshop.bestellung.model.BestellungQueryModel;
import com.swt.fahrradshop.bestellung.model.WarenkorbQueryModel;
import com.swt.fahrradshop.bestellung.query.FindWarenkorbByIdQuery;
import com.swt.fahrradshop.bestellung.valueObject.BestellungsstatusEnum;
import com.swt.fahrradshop.bestellung.valueObject.WarenkorbProdukt;
import com.swt.fahrradshop.core.commands.*;
import com.swt.fahrradshop.bestellung.query.*;
import com.swt.fahrradshop.core.events.*;
import com.swt.fahrradshop.core.models.LogistikQueryModel;
import com.swt.fahrradshop.core.models.ZahlungQueryModel;
import com.swt.fahrradshop.core.queries.FindLogistikByIdQuery;
import com.swt.fahrradshop.core.queries.FindZahlungByBestellungIdQuery;
import com.swt.fahrradshop.core.queries.FindZahlungByIdQuery;
import com.swt.fahrradshop.core.valueObject.KreditKarte;
import com.swt.fahrradshop.core.valueObject.LieferstatusEnum;
import com.swt.fahrradshop.core.valueObject.ZahlungsstatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Saga
public class BestellungSaga {
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;

    //mocked keditKarte details - JSON from frontend
    KreditKarte keditKarte = new KreditKarte(
            "123456789123456789",
            "Leo Mok",
            "08/25",
            "456"
    );

    //mocked gesamtpreis- supposed to be calculated in Frontend
    BigDecimal gesamtpreis = BigDecimal.valueOf(30);

    WarenkorbQueryModel warenkorb = null;
    LogistikQueryModel logistik = null;
    ZahlungQueryModel zahlung = null;
    BestellungQueryModel bestellung =null;

    /**
     * The logic here:
     * one order has a list of products {pro1, pro2, pro3} that needs to be reserved
     * if pro1 is reserved without any issues, but pro2 is not reserved
     * the products in this case should all be unreserved
     * this needToBeUnreserved will hold the products like pro1 which successfully
     * got reserved but due to issues with other products we need to unreserve them
     */
    List<WarenkorbProdukt> needToBeUnreserved = new ArrayList<>();

    //AtomicReference<Boolean> isReserved = new AtomicReference<>(true);

    //********************************************Saga start********************************************
    @StartSaga
    @SagaEventHandler(associationProperty = "warenkorbId")
    public void handle(WarenkorbOrderedEvent warenkorbOrderedEvent) {

        //get the KundeId from the Warenkob
        FindWarenkorbByIdQuery warenkorbByIdQuery = new FindWarenkorbByIdQuery(warenkorbOrderedEvent.getWarenkorbId());

        try {
            warenkorb = queryGateway.query(warenkorbByIdQuery,
                    ResponseTypes.instanceOf(WarenkorbQueryModel.class)).join();
            log.info("getting warenkorb : {}  from DB was successful", warenkorbOrderedEvent.getWarenkorbId());
        } catch (Exception exception) {
            log.warn(exception.getMessage());
            //compensating action
            log.info("getting warenkorb : {}  from DB was not successful", warenkorbOrderedEvent.getWarenkorbId());
            UnorderWarenkorbCommand unorderWarenkorbCommand = new UnorderWarenkorbCommand(
                    warenkorbOrderedEvent.getWarenkorbId());
            commandGateway.send(unorderWarenkorbCommand);
            return;
        }

        CreateBestellungCommand createBestellungCommand = new CreateBestellungCommand(
                UUID.randomUUID().toString(),
                BestellungsstatusEnum.ERSTELLT,
                warenkorb.getKundeId(),
                warenkorb.getWarenkorbId(),
                gesamtpreis);
        SagaLifecycle.associateWith("bestellungId", createBestellungCommand.getBestellungId());
        log.info("***createBestellungCommand handled for warenkorbId: {} and bestellungId: {} ***", createBestellungCommand.getWarenkorbId(),
                createBestellungCommand.getBestellungId());
        //-----------------------------------------Send command
        commandGateway.send(createBestellungCommand, (commandResult, throwable) -> {
            if (throwable.isExceptional()) {
                //-----------compensating action
                log.info("{}  happened while creation of bestellung: {}",
                        throwable.exceptionResult().getMessage(),
                        createBestellungCommand.getBestellungId());
                UnorderWarenkorbCommand unorderWarenkorbCommand = new UnorderWarenkorbCommand(
                        warenkorbOrderedEvent.getWarenkorbId());
                commandGateway.send(unorderWarenkorbCommand);
            } else {
                log.info("{} order is created", createBestellungCommand.getBestellungId());
            }
        });
    }

    @SagaEventHandler(associationProperty = "bestellungId")
    public void handle(BestellungCreatedEvent bestellungCreatedEvent) {
        //get the produkte ids to reserve them
        //get the warenkorb based on id

        FindWarenkorbByIdQuery warenkorbByIdQuery = new FindWarenkorbByIdQuery(bestellungCreatedEvent.getWarenkorbId());
        try {
            warenkorb = queryGateway.query(warenkorbByIdQuery,
                    ResponseTypes.instanceOf(WarenkorbQueryModel.class)).join();
            log.info("getting warenkorb : {}  from DB was successful", bestellungCreatedEvent.getWarenkorbId());
        } catch (Exception exception) {
            log.warn(exception.getMessage());
            //compensating action
            log.info("getting warenkorb : {}  from DB was not successful", bestellungCreatedEvent.getWarenkorbId());
            CancelBestellungCommand cancelBestellungCommand = new CancelBestellungCommand(
                    bestellungCreatedEvent.getBestellungId());
            commandGateway.send(cancelBestellungCommand);
            return;
        }

       /*  //---------------------------------> To katalog ---- > eureka reservation fixed
        //--the use of for loop in saga is mostly not recommended
        List<WarenkorbProdukt> produkteList = warenkorb.getProdukteList();
        // Try to reserve each produkt based on id and anzahl
       for(WarenkorbProdukt produkt: produkteList){

            ReserveProduktCommand reserveProduktCommand = new ReserveProduktCommand(produkt.getProduktId(), produkt.getProduktAnzahl());
            commandGateway.send(reserveProduktCommand, (commandResult,throwable)->{
               //if only one Produkt can't be reserved the bestellung will be canceled
                if(throwable != null){
                    log.info("Produkt with id: {}  could not be reserved! because:  {}",
                            produkt.getProduktId(),
                            throwable.exceptionResult().getMessage());
                    log.info("Bestellung with id: {}  should be canceled",bestellungCreatedEvent.getBestellungId());
                    CancelBestellungCommand cancelBestellungCommand = new CancelBestellungCommand(
                            bestellungCreatedEvent.getBestellungId());
                            commandGateway.send(cancelBestellungCommand);
                    isReserved.set(false);
                }
                else {
                    needToBeUnreserved.add(produkt);
                    log.info("Produkt with id: {} is successively reserved!"+ produkt.getProduktId());
                }
            });
            //if only one produkt reservation wan not successful break the loop
            if(!isReserved.get()){
                break;
            }
        }

        log.info("produkt liste: {}",produkteList);
        log.info("produkt to be unreserved liste: {}",needToBeUnreserved);*/
       /* //if only one product could not be reserved, unreserved all the produkte in the Bestellung
       if(needToBeUnreserved.size() != produkteList.size())
        {
            //first unreserved the reserved ones
            for(WarenkorbProdukt pro : needToBeUnreserved){
                commandGateway.send(new UnreserveProduktCommand(
                        pro.getProduktId(),
                        pro.getProduktAnzahl()
                ));
            }
            //then cancel the bestellung
            commandGateway.send(new CancelBestellungCommand(bestellungCreatedEvent.getBestellungId()));
        }*/
        /*if(isReserved.get()){
         */

        //---------------------------------> To Zahlung
        log.info("Zahlung for the bestellung: {}  is being processed", bestellungCreatedEvent.getBestellungId());
        ProcessZahlungCommand processZahlungCommand = new ProcessZahlungCommand(
                UUID.randomUUID().toString(),
                bestellungCreatedEvent.getBestellungId(),
                bestellungCreatedEvent.getGesamtpreis(),
                keditKarte,
                ZahlungsstatusEnum.IN_BEARBEITUNG);

        log.info("***ProcessZahlungCommand being handled for bestellungId: {} and zahlungId: {} ***",
                bestellungCreatedEvent.getBestellungId(),
                processZahlungCommand.getZahlungId());

                commandGateway.send(processZahlungCommand, (commandResult, throwable) -> {
            if (throwable.isExceptional()) { // to access if statement ->  put if(throwable =! null)-> cancelBestellungCommand
                //-----------compensating action
                log.info("Error while processing zahlung {}", processZahlungCommand.getZahlungId());
                log.info("for the Bestellung with id: {} -> should be canceled", processZahlungCommand.getBestellungId());
                CancelBestellungCommand cancelBestellungCommand = new CancelBestellungCommand(
                        bestellungCreatedEvent.getBestellungId());
                commandGateway.send(cancelBestellungCommand);
            } else {
                log.info("{} zahlung is created", processZahlungCommand.getZahlungId());
            }
        });
    }

    @SagaEventHandler(associationProperty = "bestellungId")
    public void handle(ZahlungProcessedEvent zahlungProcessedEvent) {

        /**
         * the if statement here is needed because ass soon as ProcessZahlungCommand
         * is sent, Zahlung status ={ABGELEHNT or BEZAHLT} is mocked in
         * the Aggregate command handler
         * */
        // ABGELEHNT
        if (zahlungProcessedEvent.getZahlungsstatusEnum().equals(ZahlungsstatusEnum.ABGELEHNT)) {
            CancelBestellungCommand cancelBestellungCommand = new CancelBestellungCommand(zahlungProcessedEvent.getBestellungId());
            commandGateway.send(cancelBestellungCommand);
            log.info("Zahlung was not successful. -> Bestellung {} will be canceled", zahlungProcessedEvent.getBestellungId());
            return;
        }
        // BEZAHLT
        if (zahlungProcessedEvent.getZahlungsstatusEnum().equals(ZahlungsstatusEnum.BEZAHLT)) {

            log.info("***Zahlung was successful. Bestellung : {} will be marked IN_BEARBEITUNG! ***",
                    zahlungProcessedEvent.getBestellungId());
            //Mark bestellung payed = IN_BEARBEITUNG for shipping
            UpdatePayedOrSentBestellungCommand payedBestellungCommand = new UpdatePayedOrSentBestellungCommand(zahlungProcessedEvent.getBestellungId());
            commandGateway.send(payedBestellungCommand);

            //-------------------------> To logistik
            CreateLogistikCommand createLogistikCommand = new CreateLogistikCommand(
                    UUID.randomUUID().toString(),
                    zahlungProcessedEvent.getBestellungId(),
                    LieferstatusEnum.BEARBEITET);

            log.info("***createLogistikCommand being handled for bestellungId: {} and logistikId: {} ***",
                    zahlungProcessedEvent.getBestellungId(),
                    createLogistikCommand.getLogistikId());

            commandGateway.send(createLogistikCommand, (commandMessage, throwable) -> {
                if (throwable.isExceptional()) {
                    //-----------compensating action
                    log.info("Unable to create the Logistik!!");
                    //cancel Zahlung -> start Refund -> delete
                    CancelZahlungCommand cancelZahlungCommand = new CancelZahlungCommand(zahlungProcessedEvent.getZahlungId());
                    commandGateway.send(cancelZahlungCommand);
                } else {
                    log.info("Logistik {} created successfully! Bestellung ready to be sent.", createLogistikCommand.getLogistikId());
                }
            });
        }
    }

  @SagaEventHandler(associationProperty = "bestellungId")
    public void handle(LogistikCreatedEvent logistikCreatedEvent){
        SendShippingCommand sendShippingCommand = new SendShippingCommand(
                logistikCreatedEvent.getLogistikId(),
                logistikCreatedEvent.getBestellungId(),
                logistikCreatedEvent.getLieferstatus()
        );
      SagaLifecycle.associateWith("logistikId", sendShippingCommand.getLogistikId());

      log.info("***sendShippingCommand being handled for bestellungId: {} and logistikId: {} ***",
              logistikCreatedEvent.getBestellungId(),
              logistikCreatedEvent.getLogistikId());
        commandGateway.send(sendShippingCommand, ((commandMessage, throwable) -> {
            if(throwable.isExceptional()){
                log.info("Stock issues with the shippment {}. It will be canceled.",logistikCreatedEvent.getLogistikId());
                CancelLogistikCommand cancelLogistikCommand = new CancelLogistikCommand(logistikCreatedEvent.getLogistikId());
                commandGateway.send(cancelLogistikCommand);
            }
            else{
                //mark it sent
                log.info("Shipping {} for the bestellung {} is on its way.",sendShippingCommand.getLogistikId(),
                        sendShippingCommand.getBestellungId());
            }
        }));
    }

    @SagaEventHandler(associationProperty = "logistikId")
    public void handle(LogistikCanceledEvent logistikCanceledEvent){
        //need the bestellung id
        FindLogistikByIdQuery findLogistikByIdQuery = new FindLogistikByIdQuery(logistikCanceledEvent.getLogistikId());
        try {
             logistik = queryGateway.query(findLogistikByIdQuery,
                    ResponseTypes.instanceOf(LogistikQueryModel.class)).join();
            log.info("getting logistik : {}  from DB was successful", logistikCanceledEvent.getLogistikId());

        } catch (Exception exception) {
            log.warn(exception.getMessage());
            log.info("getting logistik : {}  from DB was not successful", logistikCanceledEvent.getLogistikId());
            return;
        }
        //need the zahlung id to cancel it
        FindZahlungByBestellungIdQuery findZahlungByBestellungIdQuery = new FindZahlungByBestellungIdQuery(logistik.getBestellungId());
        try {
            zahlung = queryGateway.query(findZahlungByBestellungIdQuery,
                    ResponseTypes.instanceOf(ZahlungQueryModel.class)).join();
            log.info("getting zahlung with the bestellung: {}  from DB was successful", logistik.getBestellungId());

        } catch (Exception exception) {
            log.warn(exception.getMessage());
            log.info("getting zahlung with the bestellung: {}  from DB was not successful", logistik.getBestellungId());
            return;
        }

        SagaLifecycle.associateWith("bestellungId", logistik.getBestellungId());
        SagaLifecycle.associateWith("zahlungId", zahlung.getZahlungId());

        CancelZahlungCommand cancelZahlungCommand = new CancelZahlungCommand(zahlung.getZahlungId());
        commandGateway.send(cancelZahlungCommand);
    }

    @SagaEventHandler(associationProperty = "zahlungId")
    public void handle(ZahlungCanceledEvent zahlungCanceledEvent){

        //get the bestellung Id
        //need the zahlung id to cancel it
        FindZahlungByIdQuery findZahlungByIdQuery = new FindZahlungByIdQuery(zahlungCanceledEvent.getZahlungId());
        try {
            zahlung = queryGateway.query(findZahlungByIdQuery,
                    ResponseTypes.instanceOf(ZahlungQueryModel.class)).join();
            log.info("getting zahlung: {}  from DB was successful", zahlungCanceledEvent.getZahlungId());

        } catch (Exception exception) {
            log.warn(exception.getMessage());
            log.info("getting zahlung : {}  from DB was not successful", zahlungCanceledEvent.getZahlungId());
            return;
        }

        CancelBestellungCommand cancelBestellungCommand = new CancelBestellungCommand(
                zahlung.getBestellungId());
        commandGateway.send(cancelBestellungCommand);

    }

    //********************************************Saga Ends********************************************
    //-------------Happy ending :)
    @EndSaga
    @SagaEventHandler(associationProperty = "bestellungId")
    public void handle(ShippingSentEvent shippingSentEvent){
        //update status of bestellung to ABGESCHLOSSEN
        UpdatePayedOrSentBestellungCommand sentBestellungCommand = new UpdatePayedOrSentBestellungCommand(shippingSentEvent.getBestellungId());
        commandGateway.send(sentBestellungCommand);
        log.info("++++++++Saga successfully finished for {}+++++++",shippingSentEvent.getBestellungId());
    }

    //-------------Sad ending :)
    @EndSaga
    @SagaEventHandler(associationProperty="bestellungId")
    public void handle(BestellungCanceledEvent bestellungCanceledEvent){

        //get bestellung
        FindBestellungQuery findBestellungQuery = new FindBestellungQuery(bestellungCanceledEvent.getBestellungId());
        try {
            bestellung = queryGateway.query(findBestellungQuery, ResponseTypes.instanceOf(BestellungQueryModel.class)).join();
            log.info("getting bestellung: {}  from DB was successful", bestellungCanceledEvent.getBestellungId());

        } catch (Exception exception) {
            log.warn(exception.getMessage());
            log.info("getting bestellung : {}  from DB was not successful", bestellungCanceledEvent.getBestellungId());
            return;
        }

        UnorderWarenkorbCommand unorderWarenkorbCommand = new UnorderWarenkorbCommand(
                bestellung.getWarenkorbId());
        commandGateway.send(unorderWarenkorbCommand);
        log.info("Bestellung Saga aborted for Warenkorb {}: ", unorderWarenkorbCommand.getWarenkorbId());
    }


}
