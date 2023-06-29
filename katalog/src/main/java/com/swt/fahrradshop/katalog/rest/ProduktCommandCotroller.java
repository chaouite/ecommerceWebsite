package com.swt.fahrradshop.katalog.rest;



import com.swt.fahrradshop.core.commands.ReserveProduktCommand;
import com.swt.fahrradshop.core.commands.UnreserveProduktCommand;
import com.swt.fahrradshop.katalog.dto.ProduktDto;
import com.swt.fahrradshop.katalog.entity.Produkt;
import com.swt.fahrradshop.katalog.service.ProduktCommandService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
@RequestMapping
//Craeting REST API for the Commands
public class ProduktCommandCotroller {

    private final ProduktCommandService produktCommand;


        @PostMapping("/katalog/create")
        @ResponseStatus(value = HttpStatus.CREATED)
        public CompletableFuture<Produkt> createProdukt(@RequestBody ProduktDto produktDto) {return this.produktCommand.createProdukt(produktDto);
        }



        @PutMapping("/katalog/update/{produktId}")
        public CompletableFuture <Produkt> updateProdukt(@PathVariable String produktId, @RequestBody ProduktDto produktDto) {

        return this.produktCommand.updateProdukt(produktId, produktDto);
    }

        @DeleteMapping("/katalog/delete/{produktId}")
        public ResponseEntity<String> deleteProdukt(@PathVariable String produktId) {
            try {
                produktCommand.deleteProdukt(produktId);
                return ResponseEntity.ok("Produkt successfully deleted");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Failed to delete Produkt");
            }
        }


        @PostMapping("/katalog/reserve")
        public ResponseEntity<String> reserveProdukt(@RequestBody ReserveProduktCommand command) {
        produktCommand.reserveProdukt(command);
        return ResponseEntity.ok("Produkt reserved successfully");}

        @PostMapping("/katalog/unreserve")
        public ResponseEntity<String> unReserveProdukt(@RequestBody UnreserveProduktCommand command){
            produktCommand.unsreserveProdukt(command);
        return ResponseEntity.ok("Produkt unreserved Successfully");}

}






