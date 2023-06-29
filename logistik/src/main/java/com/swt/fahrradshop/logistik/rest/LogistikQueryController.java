package com.swt.fahrradshop.logistik.rest;

import com.swt.fahrradshop.core.models.LogistikQueryModel;
import com.swt.fahrradshop.core.queries.FindLogistikByIdQuery;
import com.swt.fahrradshop.logistik.query.FindLogistikenQuery;
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
public class LogistikQueryController {

    @Autowired
    private QueryGateway queryGateway;
    //with real time update
    @GetMapping(value= "/logistik", produces ="text/event-stream")
    public Flux<LogistikQueryModel> getLogistiken (){
        FindLogistikenQuery qry = new FindLogistikenQuery() ;
        return Mono.fromFuture(queryGateway.query(qry, ResponseTypes.multipleInstancesOf(LogistikQueryModel.class)))
               .flatMapMany(Flux::fromIterable);
    }


    @GetMapping(value= "/logistik/{logistikId}")
    public Mono<LogistikQueryModel> getLogistikById(@PathVariable String logistikId){
        FindLogistikByIdQuery qry = new FindLogistikByIdQuery(logistikId);
        return Mono.fromFuture(queryGateway.query(qry,LogistikQueryModel.class));
    }
}
