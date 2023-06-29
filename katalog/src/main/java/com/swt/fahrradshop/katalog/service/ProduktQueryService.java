package com.swt.fahrradshop.katalog.service;

import com.swt.fahrradshop.katalog.entity.Produkt;
import com.swt.fahrradshop.katalog.query.FindProduktQuery;
import com.swt.fahrradshop.katalog.query.FindProdukteQuery;
import lombok.AllArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProduktQueryService {

    private final QueryGateway queryGateway;
    private final EventStore eventStore;

    public CompletableFuture<Produkt> findById(String produktId) {
        return this.queryGateway.query(
                new FindProduktQuery(produktId),
                ResponseTypes.instanceOf(Produkt.class)
        );
    }

    public CompletableFuture<List<Produkt>> findAllProdukte(){

        return this.queryGateway.query(new FindProdukteQuery(),
                ResponseTypes.multipleInstancesOf(Produkt.class)

        );
    }
}
