package vttp2022.csf.assessment.server.controllers;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vttp2022.csf.assessment.server.services.RestaurantService;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping ("/cuisines")
    public ResponseEntity<String> getCuisines() {

        List<String> cuisines = restaurantService.getCuisines();

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for(String c: cuisines)
            arrBuilder.add(c);
        JsonArray result = arrBuilder.build();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());

    }
}
