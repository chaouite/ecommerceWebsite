package com.swt.fahrradshop.bestellung.rest;

import com.swt.fahrradshop.bestellung.command.AddProduktToWarenkorbCommand;
import com.swt.fahrradshop.bestellung.command.CreateWarenkorbCommand;
import com.swt.fahrradshop.bestellung.command.DeleteProduktFromWarenkorbCommand;
import com.swt.fahrradshop.bestellung.command.OrderWarenkorbCommand;
import com.swt.fahrradshop.bestellung.valueObject.WarenkorbProdukt;
import com.swt.fahrradshop.bestellung.valueObject.WarenkorbStatusEnum;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class WarenkorbCommandController {

    private final CommandGateway commandGateway;

    public WarenkorbCommandController(CommandGateway cmd) {
        this.commandGateway = cmd;
    }

    @PostMapping("/warenkorb/create/{kundeId}")
    public Mono<ResponseEntity<String>> createWarenkorb(@PathVariable String kundeId) {
        return Mono.fromCallable(() -> {
            List<WarenkorbProdukt> products = new ArrayList<>();
            CreateWarenkorbCommand cmd = CreateWarenkorbCommand.builder()
                    .warenkorbId(UUID.randomUUID().toString())
                    .KundeId(kundeId)
                    .produkte(products)
                    .warenkorbStatus(WarenkorbStatusEnum.NICHT_BESTELLT)
                    .build();
            commandGateway.send(cmd);
            return ResponseEntity.ok("Warenkorb for: " + kundeId + " is created");
        });
    }

    @PutMapping("/warenkorb/{warenkorbId}/add/{produktId}/{anzahl}")
    public Mono<ResponseEntity<String>> addProduktToWarenkorb(@PathVariable String warenkorbId,
                                                              @PathVariable String produktId,
                                                              @PathVariable Integer anzahl) {

        return Mono.fromCallable(() -> {
            AddProduktToWarenkorbCommand cmd = new AddProduktToWarenkorbCommand(
                    warenkorbId,
                    produktId,
                    anzahl);
            commandGateway.send(cmd);
            return ResponseEntity.ok(anzahl + "*Produkt : " + produktId
                    + " have been added to " + warenkorbId);
        });

    }

    //delete only by one
    @PutMapping("/warenkorb/{warenkorbId}/delete/{produktId}")
    public Mono<ResponseEntity<String>> deleteProduktToWarenkorb(@PathVariable String warenkorbId,
                                                                 @PathVariable String produktId) {
        return Mono.fromCallable(() -> {
            DeleteProduktFromWarenkorbCommand cmd = new DeleteProduktFromWarenkorbCommand(
                    warenkorbId,
                    produktId);
            commandGateway.send(cmd);
            return ResponseEntity.ok("Produkt : " + produktId
                    + " is deleted from " + warenkorbId);
        });
    }

    @PutMapping("/warenkorb/{warenkorbId}/order")
    public Mono<ResponseEntity<String>> orderWarenkorb(@PathVariable String warenkorbId) {
        return Mono.fromCallable(() -> {
            OrderWarenkorbCommand cmd = new OrderWarenkorbCommand(warenkorbId);
            commandGateway.send(cmd);
            return ResponseEntity.ok("warenkorb : " + warenkorbId
                    + " is ordered");
        });
    }

    @PutMapping("/warenkorb/{warenkorbId}/unorder")
    public Mono<ResponseEntity<String>> unorderWarenkorb(@PathVariable String warenkorbId) {
        return Mono.fromCallable(() -> {
            OrderWarenkorbCommand cmd = new OrderWarenkorbCommand(warenkorbId);
            commandGateway.send(cmd);
            return ResponseEntity.ok("warenkorb : " + warenkorbId
                    + " is unordered!!");
        });
    }


}
