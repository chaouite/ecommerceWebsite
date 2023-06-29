package com.swt.fahrradshop.query_handler;

import com.swt.fahrradshop.core.queries.FindZahlungByBestellungIdQuery;
import com.swt.fahrradshop.entity.ZahlungEntity;
import com.swt.fahrradshop.core.models.ZahlungQueryModel;
import com.swt.fahrradshop.core.queries.FindZahlungByIdQuery;
import com.swt.fahrradshop.repository.ZahlungRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ZahlungQueryHandler {

    private ZahlungRepository zahlungRepository;

    public ZahlungQueryHandler(ZahlungRepository zahlungRepository) {
        this.zahlungRepository = zahlungRepository;
    }

    @QueryHandler
    public ZahlungQueryModel findZahlungById(FindZahlungByIdQuery qry) {
        ZahlungEntity zahlung = zahlungRepository.findZahlungEntitiesByZahlungId(qry.getZahlungId());
        ZahlungQueryModel zahlungQueryModel = new ZahlungQueryModel();
        BeanUtils.copyProperties(zahlung, zahlungQueryModel);
        return zahlungQueryModel;
    }

    @QueryHandler
    public ZahlungQueryModel findZahlungByBestellungId(FindZahlungByBestellungIdQuery qry) {
        ZahlungEntity zahlung = zahlungRepository.findZahlungByBestellungId(qry.getBestellungId());
        ZahlungQueryModel zahlungQueryModel = new ZahlungQueryModel();
        BeanUtils.copyProperties(zahlung, zahlungQueryModel);
        return zahlungQueryModel;
    }


}
