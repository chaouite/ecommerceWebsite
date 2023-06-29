# Fahrradshop Website - Meilenstein 1 

_PTI09420 -Projekt Softwaretechnologie SoSe23- Westsächsische Hoschule Zwickau_

**Projektteam-Mitglieder**     
- Amal Chaouite   
- Ayoub El Kendi  
- Christian Spitzer    

## Inhalt
- Domain Storytelling 
- Event Storming mit Bounded Context
- Context Map
- Event Modelling
- Core Domains
- Aggregates
- Specification By Example - Use Cases

## Domain Storytelling 
![Domain Storytelling für Fahrrad-Website - Meilenstein 1](https://media.github.fh-zwickau.de/user/96/files/a56772d6-8514-47c9-b0b8-c48e59e21c39)

## Event Storming mit Bounded Context

#### Warenkorb
![Warenkorb_Context](https://media.github.fh-zwickau.de/user/96/files/96a25675-e2ab-4d67-9b53-c3871bab2091)
#### Produkt-Katalog
![Podukt-Katalog_Context](https://media.github.fh-zwickau.de/user/96/files/e2e1b3ef-cfda-481f-996d-afdb02976c7a)
#### Bestellung
![Bestellung_Context](https://media.github.fh-zwickau.de/user/96/files/2caa352f-116d-4691-956b-0eb336212f6a)
#### Zahlung
![Zahlung_Context](https://media.github.fh-zwickau.de/user/96/files/6f99c64e-3d73-40cd-acbd-4372fd7ef617)
#### Logistik
![Logistik_Context](https://media.github.fh-zwickau.de/user/96/files/f14867b4-87cd-4f4f-bcfe-1382fdaf5a6e)
#### Lager
![Lager_Context](https://media.github.fh-zwickau.de/user/96/files/c7fb92fa-e7dd-43d4-9403-3bf576d377a3)
#### Lieferung
![Lieferung_Context](https://media.github.fh-zwickau.de/user/96/files/32da3382-968f-42ae-aa2d-2a8c2d30a9ae)
#### Rechnung
![Rechnung_Context](https://media.github.fh-zwickau.de/user/96/files/8a37ad9f-23fd-49f3-95dc-23c389b8a5a4)    


## Event Modelling
![EventModelling](https://media.github.fh-zwickau.de/user/96/files/07b5718f-6d55-43f7-ac2e-fae0fda78525)


## Specification By Example - Use Cases

#### Szenario 1: Normal erfolgreich Durchlauf des Systems
![Sczenario1](https://media.github.fh-zwickau.de/user/96/files/86d9164e-050e-437c-9383-7541071de353)

#### Szenario 2: Artikel schon bezahlt, aber nach einer Störung das Fahrrad ist nicht mehr verfügbar
![Sczenario2](https://media.github.fh-zwickau.de/user/96/files/400d6e34-ecf7-45f4-ad6d-db1b303309d6)

#### Szenario 3: Die für die Registrierung verwendete E-Mail ist ungültig
![Sczenario3](https://media.github.fh-zwickau.de/user/96/files/ac41d3e1-dd00-45fa-bae0-8b85d5a358e8)

#### Szenario 4: Die vom Käufer eingegebenen Zahlungsdaten sind ungültig 
![Sczenario4](https://media.github.fh-zwickau.de/user/96/files/5451bfcf-9634-41f8-866a-bef3bd42a195)

#### Szenario 5: Das Produkt entsprach nicht den Erwartungen des Käufers, was zu einer Rückgabeaufforderung führte 
![Sczenario5](https://media.github.fh-zwickau.de/user/96/files/017fc8f7-9242-4eee-934c-68db07f543a9)

## Bestellung context map
<img width="717" alt="context bestellung" src="https://media.github.fh-zwickau.de/user/96/files/b92370ba-a215-4238-9ff7-84c58a840203">

## Zahlung context map
<img width="522" alt="context zahlung" src="https://media.github.fh-zwickau.de/user/96/files/98a9ee43-54c7-42ec-8bf3-aec374a351a8">

## Logistik context map
![Logistik_Context_Map](https://media.github.fh-zwickau.de/user/255/files/2341e7f1-b10e-4e0c-bf6e-8823e97597b4)

## Produkt context map
![katalog](https://media.github.fh-zwickau.de/user/255/files/e89f1785-4b51-4ec8-9ebe-9feb5aafb6b2)

## Liste aller Commands

**Bestellung microservice**
commands -> warenkorb
- CreateWarenkorbCommand
- AddProduktToWarenkorbCommand
- DeleteProduktFromWarenkorbCommand
- OrderWarenkorbCommand
- UnorderWarenkorbCommand

queries ->warenkorb
- FindWarenkoerbeQuery
- FindWarenkorbByIdQuery

commands -> bestellung
- CreateBestellungCommand
- CancelBestellungCommand
- UpdatePayedOrSentBestellungCommand

queries -> bestellung
- FindBestellungenQuery
- FindBestellungQuery

**Zahlung microservice**
commands:
- ProcessZahlungCommand
- CancelZahlungCommand

queries: 
- FindZahlungByBestellungIdQuery
- FindZahlungByIdQuery

**Logistik microservice**
commands: 
- CreateLogistikCommand
- CancelLogistikCommand
- SendShippingCommand

queries: 
- FindLogistikByIdQuery
- FindLogistikenQuery

**Katalog microservice**
commands: 
- CreateProduktCommand
- UpdateProduktCommand
- DeleteProduktCommand
- ReservationProduktCommand
- UnreserveProduktCommand

queries:
- FindProduktQuery
- FindAllProduktQuery

## BestellungSaga workflow
https://excalidraw.com/#json=Jy-NNb-sjufTpNMeDa9EU,Ilg4-os783eKRbVm-KSU_w

![SAGA_Workflow](https://media.github.fh-zwickau.de/user/255/files/8609c1fc-e91f-4519-88cc-e7d365188f9e)

## Video
https://drive.google.com/file/d/16HYX4DtmDMmS7-v3BLHF4p4TgKB4XPPp/view?usp=sharing

![Saga.png](..%2F..%2F..%2FDownloads%2FSaga.png)

**NOTE**
Nicht Funktionnierende Funktionen:
 - Docker-Compose Setup (Branch: Docker (95% Vollständig))
 - Keyclock (Branch: origin/Keyclock (85% Vollständig))
