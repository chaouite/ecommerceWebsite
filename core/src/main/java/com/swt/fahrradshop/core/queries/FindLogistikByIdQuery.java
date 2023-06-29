package com.swt.fahrradshop.core.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindLogistikByIdQuery {
    private String logistikToBeFoundId;
}
