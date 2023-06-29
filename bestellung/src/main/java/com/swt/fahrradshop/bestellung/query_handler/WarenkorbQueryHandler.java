package com.swt.fahrradshop.bestellung.query_handler;

import com.swt.fahrradshop.bestellung.entity.WarenkorbEntity;
import com.swt.fahrradshop.bestellung.model.WarenkorbQueryModel;
import com.swt.fahrradshop.bestellung.query.FindWarenkoerbeQuery;
import com.swt.fahrradshop.bestellung.query.FindWarenkorbByIdQuery;
import com.swt.fahrradshop.bestellung.repository.WarenkorbRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class WarenkorbQueryHandler {
    @Autowired
    private WarenkorbRepository warenkorbRepository;

    @QueryHandler
    @Transactional
    public List<WarenkorbQueryModel> findWarenkoerbe(FindWarenkoerbeQuery qry) {
        List<WarenkorbQueryModel> warenkoerbe = new ArrayList<>();
        List<WarenkorbEntity> warenkoerbeInDB = warenkorbRepository.findAll();

        for (WarenkorbEntity warenkorb : warenkoerbeInDB) {
            //for lazy loading
            warenkorb.getProdukteList().size();
            WarenkorbQueryModel warenkorbQueryModel = WarenkorbQueryModel.builder()
                    .warenkorbId(warenkorb.getWarenkorbId())
                    .KundeId(warenkorb.getKundeId())
                    .produkteList(warenkorb.getProdukteList())
                    .warenkorbStatus(warenkorb.getWarenkorbStatus())
                    .build();
            warenkoerbe.add(warenkorbQueryModel);
        }
        return warenkoerbe;
    }

    @QueryHandler
    @Transactional
    public WarenkorbQueryModel getWarenkorbById(FindWarenkorbByIdQuery qry) {
        WarenkorbEntity warenkorbInDB = warenkorbRepository.findByWarenkorbId(qry.getWarenkorbId());
        //for lazy loading
        warenkorbInDB.getProdukteList().size();
        return WarenkorbQueryModel.builder()
                .warenkorbId(warenkorbInDB.getWarenkorbId())
                .KundeId(warenkorbInDB.getKundeId())
                .produkteList(warenkorbInDB.getProdukteList())
                .warenkorbStatus(warenkorbInDB.getWarenkorbStatus())
                .build();
    }

}
