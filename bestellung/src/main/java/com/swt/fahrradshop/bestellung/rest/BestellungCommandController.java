package com.swt.fahrradshop.bestellung.rest;

import com.swt.fahrradshop.bestellung.command.CancelBestellungCommand;
import com.swt.fahrradshop.bestellung.command.CreateBestellungCommand;
import com.swt.fahrradshop.bestellung.command.UpdatePayedOrSentBestellungCommand;
import com.swt.fahrradshop.bestellung.model.BestellungCommandModel;
import com.swt.fahrradshop.bestellung.valueObject.BestellungsstatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@Slf4j
public class BestellungCommandController {

    private final CommandGateway commandGateway;

    public BestellungCommandController(CommandGateway cmd) {
        this.commandGateway = cmd;
    }

    @PostMapping("/bestellung/create")
    public Mono<ResponseEntity<String>> createBestellung(@RequestBody BestellungCommandModel bestellung) {
        return Mono.fromCallable(() -> {
            CreateBestellungCommand cmd = CreateBestellungCommand.builder()
                    .bestellungId(UUID.randomUUID().toString())
                    .bestellungsstatus(BestellungsstatusEnum.ERSTELLT)
                    .kundeId(bestellung.getKundeId())
                    .warenkorbId(bestellung.getWarenkorbId())
                    .gesamtpreis(bestellung.getGesamtpreis())
                    .build();
            commandGateway.send(cmd);
            return ResponseEntity.ok("Bestellung " + bestellung + " is created.");
        });
    }

    @DeleteMapping("/bestellung/cancel/{bestellungId}")
    public Mono<ResponseEntity<String>> cancelBestellung(@PathVariable String bestellungId) {
        return Mono.fromCallable(() -> {
            CancelBestellungCommand cmd = CancelBestellungCommand.builder().bestellungId(bestellungId).build();
            commandGateway.send(cmd);
            return ResponseEntity.ok("Bestellung: " + bestellungId + " is canceled.");
        });
    }

    @PutMapping("/bestellung/payed/{bestellungId}")
    public Mono<ResponseEntity<String>> updatePayedBestellungStatus(@PathVariable String bestellungId) {
        return Mono.fromCallable(() -> {
            UpdatePayedOrSentBestellungCommand cmd = UpdatePayedOrSentBestellungCommand.builder().bestellungId(bestellungId).build();
            commandGateway.send(cmd);
            return ResponseEntity.ok("Bestellung: " + bestellungId + " is now payed.");
        });
    }

    @PutMapping("/bestellung/sent/{bestellungId}")
    public Mono<ResponseEntity<String>> updateSentBestellungStatus(@PathVariable String bestellungId) {
        return Mono.fromCallable(() -> {
            UpdatePayedOrSentBestellungCommand cmd = UpdatePayedOrSentBestellungCommand.builder().bestellungId(bestellungId).build();
            commandGateway.send(cmd);
            return ResponseEntity.ok("Bestellung: " + bestellungId + " is now sent.");
        });
    }
}
