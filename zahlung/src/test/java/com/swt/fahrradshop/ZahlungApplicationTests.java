package com.swt.fahrradshop;

import com.swt.fahrradshop.aggregate.ZahlungAggregate;
import com.swt.fahrradshop.core.commands.ProcessZahlungCommand;
import com.swt.fahrradshop.core.events.ZahlungProcessedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.swt.fahrradshop.core.valueObject.KreditKarte;
import com.swt.fahrradshop.core.valueObject.ZahlungsstatusEnum;

import java.math.BigDecimal;


@SpringBootTest
class ZahlungTests {
    private FixtureConfiguration<ZahlungAggregate> fixture;

    private KreditKarte kreditKarte = new KreditKarte(
            "123456789123456789",
            "LEO MESSI",
            "08/25",
            "888"
    );

    ZahlungProcessedEvent evt1 = new ZahlungProcessedEvent(
            "---ZAHLUNG--ID---",
            "---BESTELLUNG--ID---",
            BigDecimal.valueOf(1040),
            kreditKarte,
            ZahlungsstatusEnum.BEZAHLT
    );


    @BeforeEach
    public void init() {

        fixture = new AggregateTestFixture<>(ZahlungAggregate.class);
    }

    @Test
    void testProcessZahlung() {
        fixture.given()
                .when(new ProcessZahlungCommand(
                        "---ZAHLUNG--ID---",
                        "---BESTELLUNG--ID---",
                        BigDecimal.valueOf(1040),
                        kreditKarte,
                        ZahlungsstatusEnum.IN_BEARBEITUNG
                )).expectEvents(evt1); //can crash since the Zahlungstatus is
        //mocked randomly
    }
}
