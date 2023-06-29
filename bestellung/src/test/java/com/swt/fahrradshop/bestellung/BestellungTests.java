package com.swt.fahrradshop.bestellung;

import com.swt.fahrradshop.bestellung.aggregate.BestellungAggregate;
import com.swt.fahrradshop.bestellung.command.CancelBestellungCommand;
import com.swt.fahrradshop.bestellung.command.CreateBestellungCommand;
import com.swt.fahrradshop.bestellung.command.UpdatePayedOrSentBestellungCommand;
import com.swt.fahrradshop.bestellung.event.BestellungCreatedEvent;
import com.swt.fahrradshop.bestellung.event.PayedOrSentBestellungUpdatedEvent;
import com.swt.fahrradshop.bestellung.valueObject.BestellungsstatusEnum;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

class BestellungTests {
    private FixtureConfiguration<BestellungAggregate> fixture;
    //Values used for testing
    String bestellungId = UUID.randomUUID().toString();
    String kundeId = "XXLeoXXMessiXX";
    String warenkorbId = "XXABCXXABCXX";
    BigDecimal gesamtpreis = BigDecimal.valueOf(1040);

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(BestellungAggregate.class);
    }

    @Test
    void testCreateBestellung() {
        fixture.given()
                .when(new CreateBestellungCommand(bestellungId,
                        BestellungsstatusEnum.ERSTELLT,
                        kundeId,
                        warenkorbId,
                        gesamtpreis))
                .expectEvents(new BestellungCreatedEvent(bestellungId,
                        BestellungsstatusEnum.ERSTELLT,
                        kundeId,
                        warenkorbId,
                        gesamtpreis));
    }

    @Test
    void testUpdatePayedBestellungStatus() {
        fixture.given(new BestellungCreatedEvent(bestellungId,
                        BestellungsstatusEnum.ERSTELLT,
                        kundeId,
                        warenkorbId,
                        gesamtpreis))
                .when(new UpdatePayedOrSentBestellungCommand(bestellungId))
                .expectEvents(new PayedOrSentBestellungUpdatedEvent(bestellungId,
                        BestellungsstatusEnum.IN_BEARBEITUNG));
    }

    @Test
    void testUpdateSentBestellungStatus() {
        fixture.given(new PayedOrSentBestellungUpdatedEvent(bestellungId,
                        BestellungsstatusEnum.IN_BEARBEITUNG))
                .when(new UpdatePayedOrSentBestellungCommand(bestellungId))
                .expectEvents(new PayedOrSentBestellungUpdatedEvent(bestellungId,
                        BestellungsstatusEnum.ABGESCHLOSSEN));
    }

    @Test
    void testCancelBestellung() {
        fixture.given(new BestellungCreatedEvent(bestellungId,
                        BestellungsstatusEnum.ERSTELLT,
                        kundeId,
                        warenkorbId,
                        gesamtpreis))
                .when(new CancelBestellungCommand(bestellungId))
                .expectSuccessfulHandlerExecution();
    }


}

