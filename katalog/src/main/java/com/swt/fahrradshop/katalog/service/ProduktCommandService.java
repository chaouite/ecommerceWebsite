package com.swt.fahrradshop.katalog.service;

import com.swt.fahrradshop.core.commands.ReserveProduktCommand;
import com.swt.fahrradshop.core.commands.UnreserveProduktCommand;
import com.swt.fahrradshop.katalog.command.*;
import com.swt.fahrradshop.katalog.dto.ProduktDto;
import com.swt.fahrradshop.katalog.entity.Produkt;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Service
@AllArgsConstructor
public class ProduktCommandService {
    //CommandGateway for dispatching commands
    private final CommandGateway commandGateway;


    public CompletableFuture<Produkt> createProdukt (ProduktDto produktdto) {

        CreateProduktCommand command = new CreateProduktCommand(UUID.randomUUID().toString(),produktdto.getName(),
                produktdto.getPreis(),
                produktdto.getAnzahl(),
                produktdto.getKategorie(),
                produktdto.getVerfuegbarkeit(),
                BigDecimal.valueOf(0));
                return commandGateway.send(command);

    }

    public CompletableFuture<Produkt> updateProdukt(String produktId,ProduktDto produktdto){

    UpdateProduktCommand command = new UpdateProduktCommand(produktId, produktdto.getName(),
            produktdto.getPreis(),
            produktdto.getAnzahl(),
            produktdto.getKategorie(),
            produktdto.getVerfuegbarkeit(),
            produktdto.getAnzahlToReserve());
        return this.commandGateway.send(command);
    }
    public CompletableFuture<String> deleteProdukt(String produktId) {
        return commandGateway.send(new DeleteProduktCommand(produktId));
    }
    public void reserveProdukt(ReserveProduktCommand command) {
        commandGateway.sendAndWait(command);
    }
    public void unsreserveProdukt(UnreserveProduktCommand command){

        commandGateway.sendAndWait(command);
 }
}


