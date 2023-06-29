package com.swt.fahrradshop.logistik.query_handler;

import com.swt.fahrradshop.logistik.entity.LogistikEntity;
import com.swt.fahrradshop.core.models.LogistikQueryModel;
import com.swt.fahrradshop.core.queries.FindLogistikByIdQuery;
import com.swt.fahrradshop.logistik.query.FindLogistikenQuery;
import com.swt.fahrradshop.logistik.repository.LogistikRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class LogistikQueryHandler {

    @Autowired
    private LogistikRepository logistikRepository;

    @QueryHandler
    public List<LogistikQueryModel> findLogistiken(FindLogistikenQuery qry){
        List<LogistikQueryModel> logistiken = new ArrayList<>();

        List<LogistikEntity> logistikenInDB = logistikRepository.findAll();

        if(logistikenInDB.size() == 0){
            log.info("Logistiken DB is empty");
        }
        else{
            for (LogistikEntity logistik : logistikenInDB){
                LogistikQueryModel logistikQueryModel = new LogistikQueryModel();
                BeanUtils.copyProperties(
                        logistik,logistikQueryModel
                );
                logistiken.add(logistikQueryModel);
            }}
        return logistiken;
    }

    @QueryHandler
    public LogistikQueryModel findLogistikById(FindLogistikByIdQuery qry){
        LogistikEntity logistikInDB=logistikRepository.findByLogistikId(qry.getLogistikToBeFoundId());
        LogistikQueryModel logistik = new LogistikQueryModel();
        BeanUtils.copyProperties(logistikInDB,logistik);
        return logistik;
    }


} 
