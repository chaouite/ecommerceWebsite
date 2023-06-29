package com.swt.fahrradshop.bestellung;

import com.swt.fahrradshop.bestellung.aggregate.WarenkorbAggregate;
import com.swt.fahrradshop.bestellung.command.AddProduktToWarenkorbCommand;
import com.swt.fahrradshop.bestellung.command.CreateWarenkorbCommand;
import com.swt.fahrradshop.bestellung.command.DeleteProduktFromWarenkorbCommand;
import com.swt.fahrradshop.bestellung.command.OrderWarenkorbCommand;
import com.swt.fahrradshop.bestellung.event.ProduktFromWarenkorbDeletedEvent;
import com.swt.fahrradshop.bestellung.event.ProduktToWarenkorbAddedEvent;
import com.swt.fahrradshop.bestellung.event.WarenkorbCreatedEvent;
import com.swt.fahrradshop.bestellung.event.WarenkorbOrderedEvent;
import com.swt.fahrradshop.bestellung.valueObject.WarenkorbProdukt;
import com.swt.fahrradshop.bestellung.valueObject.WarenkorbStatusEnum;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WarenkorbTests {

    private FixtureConfiguration<WarenkorbAggregate> fixture;
    //Values used for testing
    String warenkorbId = UUID.randomUUID().toString();
    String kundeId = "XXLeoXXMessiXX";
    List<WarenkorbProdukt> produkte = new ArrayList<>();
    String produktId = "F--A--H--R--R--A--D";
    Integer anzahl = 8;

    @BeforeEach
    public void init() {
        fixture = new AggregateTestFixture<>(WarenkorbAggregate.class);
    }

    @Test
    void testCreateWarenkorb() {
        fixture.given()
                .when(new CreateWarenkorbCommand(
                        warenkorbId,
                        kundeId,
                        produkte,
                        WarenkorbStatusEnum.NICHT_BESTELLT))
                .expectEvents(new WarenkorbCreatedEvent(
                        warenkorbId,
                        kundeId,
                        produkte,
                        WarenkorbStatusEnum.NICHT_BESTELLT)
                );
    }

    @Test
    void testAddProduktToWarenkorb() {
        fixture.given(new WarenkorbCreatedEvent(
                        warenkorbId,
                        kundeId,
                        produkte,
                        WarenkorbStatusEnum.NICHT_BESTELLT))
                .when(new AddProduktToWarenkorbCommand(
                        warenkorbId,
                        produktId,
                        anzahl))
                .expectEvents(new ProduktToWarenkorbAddedEvent(
                        warenkorbId,
                        produktId,
                        anzahl)
                );
    }

    @Test
    void testDeleteProduktFromWarenkorb() {
        fixture.given(new ProduktToWarenkorbAddedEvent(
                        warenkorbId,
                        produktId,
                        anzahl))
                .when(new DeleteProduktFromWarenkorbCommand(
                        warenkorbId,
                        produktId))
                .expectEvents(new ProduktFromWarenkorbDeletedEvent(
                        warenkorbId,
                        produktId)
                );
    }

    @Test
    void testOrderWarenkorb() {
        fixture.given(new WarenkorbCreatedEvent(
                        warenkorbId,
                        kundeId,
                        produkte,
                        WarenkorbStatusEnum.NICHT_BESTELLT))
                .when(new OrderWarenkorbCommand(
                        warenkorbId))
                .expectEvents(new WarenkorbOrderedEvent(
                        warenkorbId)
                );
    }

}

