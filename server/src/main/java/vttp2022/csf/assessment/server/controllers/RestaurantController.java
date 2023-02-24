package vttp2022.csf.assessment.server.controllers;

import jakarta.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;
import vttp2022.csf.assessment.server.services.RestaurantService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{cuisine}/restaurants")
    public ResponseEntity<String> getRestaurantsByCuisine(@PathVariable String cuisine){

        List<String> restaurants = restaurantService.getRestaurantsByCuisine(cuisine);

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for(String rest: restaurants)
            arrBuilder.add(rest);
        JsonArray result = arrBuilder.build();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping("/restaurant")
    public ResponseEntity<String> getRestaurantByName(@RequestParam String name) throws IOException {
       Optional<Restaurant> optRest =  restaurantService.getRestaurant(name);

       Restaurant restaurant = optRest.get();

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        arrBuilder.add(restaurant.getCoordinates().getLongitude());
        arrBuilder.add(restaurant.getCoordinates().getLatitude());
        JsonArray result = arrBuilder.build();

       JsonObject restObj = Json.createObjectBuilder()
               .add("restaurantId", restaurant.getRestaurantId())
               .add("name", restaurant.getName())
               .add("cuisine", restaurant.getCuisine())
               .add("address", restaurant.getAddress())
               .add("coordinates", result)
               .add("imgUrl", restaurant.getMapURL())
               .build();


       return ResponseEntity.status(HttpStatus.OK)
               .contentType(MediaType.APPLICATION_JSON)
               .body(restObj.toString());
    }

    @PostMapping(path = "/comments", consumes = "application/json")
    public ResponseEntity<String> postComment(@RequestBody String body){

        System.out.println(body.toString());
        Comment comment = new Comment();
        InputStream inputStream = new ByteArrayInputStream(body.getBytes());
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();

        comment.setRestaurantId(jsonObject.getString("restaurantId"));
        comment.setRating(jsonObject.getInt("rating"));
        comment.setText(jsonObject.getString("comment"));
        comment.setName(jsonObject.getString("name"));

        restaurantService.addComment(comment);

        JsonObject response = Json.createObjectBuilder()
                .add("message", "Comment posted")
                .build();

        return ResponseEntity.ok(response.toString());

    }
}
