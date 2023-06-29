package com.swt.fahrradshop.logistik.entity;

import lombok.*;


import javax.persistence.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logistik")

public class LogistikEntity {
    @Id
    private String logistikId;
    
    private String bestellungId;
    private String lieferstatus;
    
    
}
