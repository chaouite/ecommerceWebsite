package com.swt.fahrradshop.logistik.rest;

import com.swt.fahrradshop.core.commands.CreateLogistikCommand;
import com.swt.fahrradshop.core.commands.CancelLogistikCommand;
import com.swt.fahrradshop.core.commands.SendShippingCommand;
import com.swt.fahrradshop.logistik.model.LogistikCommandModel;
import com.swt.fahrradshop.core.valueObject.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@Slf4j
public class LogistikCommandController {

    private final CommandGateway commandGateway;
    public LogistikCommandController(CommandGateway cmd)
    {
        this.commandGateway = cmd;
    }

    @PostMapping("/logistik/create")
    public Mono<ResponseEntity<String>> createLogistik (@RequestBody LogistikCommandModel logistik){
        return Mono.fromCallable(() -> {
            CreateLogistikCommand cmd = CreateLogistikCommand.builder()
                    .logistikId(UUID.randomUUID().toString())
                    .bestellungId(logistik.getBestellungId())
                    .lieferstatusEnum(LieferstatusEnum.BEARBEITET)
                    .build();
            commandGateway.send(cmd);
            return ResponseEntity.ok("Logistik " + logistik +" is created.");
        });
    }
    @DeleteMapping("/logistik/cancel/{logistikId}")
    public Mono<ResponseEntity<String>> cancelLogisitik(@PathVariable String logistikId){
        return Mono.fromCallable(() -> {
            CancelLogistikCommand cmd = CancelLogistikCommand.builder().logistikId(logistikId).build();
            commandGateway.send(cmd);
            return ResponseEntity.ok("Logistik: " + logistikId + " is canceled.");
        });
    }

    @PutMapping("/logistik/deliver/{logistikId}")
    public Mono<ResponseEntity<String>> sendShippingStatus(@PathVariable String logistikId){
        return Mono.fromCallable(() -> {
            SendShippingCommand cmd = SendShippingCommand.builder().logistikId(logistikId).build();
            commandGateway.send(cmd);
            return ResponseEntity.ok("Logistik: " + logistikId + " has sent.");
        });
    }
}
