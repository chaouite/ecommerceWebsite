package com.swt.fahrradshop.rest;

import com.swt.fahrradshop.core.queries.FindZahlungByBestellungIdQuery;
import com.swt.fahrradshop.core.models.ZahlungQueryModel;
import com.swt.fahrradshop.core.queries.FindZahlungByIdQuery;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class ZahlungQueryController {
    private final QueryGateway queryGateway;

    public ZahlungQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/zahlung/{zahlungId}")
    public Mono<ZahlungQueryModel> getStatus(@PathVariable String zahlungId) {
        FindZahlungByIdQuery qry = new FindZahlungByIdQuery(zahlungId);
        return Mono.fromFuture(queryGateway.query(qry, ZahlungQueryModel.class));
    }

    @GetMapping("/zahlung/{bestellungId}")
    public Mono<ZahlungQueryModel> getZahungByBestellungId(@PathVariable String bestellungId) {
        FindZahlungByBestellungIdQuery qry = new FindZahlungByBestellungIdQuery(bestellungId);
        return Mono.fromFuture(queryGateway.query(qry, ZahlungQueryModel.class));
    }
}
