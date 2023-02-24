package vttp2022.csf.assessment.server.services;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;
import vttp2022.csf.assessment.server.repositories.MapCache;
import vttp2022.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepo;

	@Autowired
	private MapCache mapCache;

	// TODO Task 2

	public List<String> getCuisines() {

		List<String> cuisines = restaurantRepo.getCuisines();

		List<String> underScoreCuisines = new LinkedList<>();

		for (String cuisine: cuisines) {
			String cuisineReplaced = cuisine.replace("/", "_");
			underScoreCuisines.add(cuisineReplaced);
		}


		return underScoreCuisines;
		
	}

	// TODO Task 3 
	// Use the following method to get a list of restaurants by cuisine
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	public List<String> getRestaurantsByCuisine(String cuisine) {
		// Implmementation in here
		String cuisineReplaced = cuisine.replace("_", "/");

		return restaurantRepo.getRestaurantsByCuisine(cuisineReplaced);
		
	}

	// TODO Task 4
	// Use this method to find a specific restaurant
	// You can add any parameters (if any) 
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	public Optional<Restaurant> getRestaurant(String name) throws IOException {
		// Implmementation in here
		Optional<Restaurant> optionalRestaurant = restaurantRepo.getRestaurant(name);
		Restaurant restaurant = optionalRestaurant.get();
		String url = mapCache.getMap(restaurant.getCoordinates());

		restaurant.setMapURL(url);

		System.out.println(restaurant.toString());

		return Optional.ofNullable(restaurant);

	}

	// TODO Task 5
	// Use this method to insert a comment into the restaurant database
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	public void addComment(Comment comment) {
		// Implmementation in here
		
	}
	//
	// You may add other methods to this class
}
