package com.swt.fahrradshop.logistik.repository;

import com.swt.fahrradshop.logistik.entity.LogistikEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogistikRepository extends JpaRepository<LogistikEntity, String> {
    LogistikEntity findByLogistikId(String logistikId);
}