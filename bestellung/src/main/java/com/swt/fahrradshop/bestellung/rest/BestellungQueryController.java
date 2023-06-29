package com.swt.fahrradshop.bestellung.rest;

import com.swt.fahrradshop.bestellung.model.BestellungQueryModel;
import com.swt.fahrradshop.bestellung.query.FindBestellungQuery;
import com.swt.fahrradshop.bestellung.query.FindBestellungenQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping
public class BestellungQueryController {

    @Autowired
    private QueryGateway queryGateway;

    //with real time update
    @GetMapping(value = "/bestellungen", produces = "text/event-stream")
    public Flux<BestellungQueryModel> getBestellungen() {
        FindBestellungenQuery qry = new FindBestellungenQuery();
        return Mono.fromFuture(queryGateway.query(qry, ResponseTypes.multipleInstancesOf(BestellungQueryModel.class)))
                .flatMapMany(Flux::fromIterable);
    }


    @GetMapping(value = "/bestellungen/{bestellungId}")
    public Mono<BestellungQueryModel> getBestellungById(@PathVariable String bestellungId) {
        FindBestellungQuery qry = new FindBestellungQuery(bestellungId);
        return Mono.fromFuture(queryGateway.query(qry, BestellungQueryModel.class));
    }
}
