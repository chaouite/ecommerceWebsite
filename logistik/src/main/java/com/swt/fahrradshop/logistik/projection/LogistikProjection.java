package com.swt.fahrradshop.logistik.projection;

import com.swt.fahrradshop.logistik.entity.LogistikEntity;
import com.swt.fahrradshop.core.events.LogistikCanceledEvent;
import com.swt.fahrradshop.core.events.LogistikCreatedEvent;
import com.swt.fahrradshop.core.events.ShippingSentEvent;
import com.swt.fahrradshop.logistik.repository.LogistikRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogistikProjection {

    private final LogistikRepository logistikRepository;

    public LogistikProjection(LogistikRepository logistikRepository) {
        this.logistikRepository = logistikRepository;
    }

    @EventHandler
    public void on(LogistikCreatedEvent evt){

        LogistikEntity logistik = new LogistikEntity(
                evt.getLogistikId(),
                evt.getBestellungId(),
                evt.getLieferstatus().toString()
        );
        logistikRepository.save(logistik);
    }

    @EventHandler
    public void on(LogistikCanceledEvent evt){
        logistikRepository.deleteById(evt.getLogistikId());
    }

    @EventHandler
    public void on(ShippingSentEvent evt){
       LogistikEntity logistik =  logistikRepository.findByLogistikId(evt.getLogistikId());
        logistik.setLieferstatus(evt.getLieferstatusEnum().toString());
    }

}
