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

	public List<String> getCuisines() {

		List<String> cuisines = restaurantRepo.getCuisines();

		List<String> underScoreCuisines = new LinkedList<>();

		for (String cuisine: cuisines) {
			String cuisineReplaced = cuisine.replace("/", "_");
			underScoreCuisines.add(cuisineReplaced);
		}


		return underScoreCuisines;
		
	}

	public List<String> getRestaurantsByCuisine(String cuisine) {
		// Implmementation in here
		String cuisineReplaced = cuisine.replace("_", "/");

		return restaurantRepo.getRestaurantsByCuisine(cuisineReplaced);
		
	}

	public Optional<Restaurant> getRestaurant(String name) throws IOException {
		// Implmementation in here
		Optional<Restaurant> optionalRestaurant = restaurantRepo.getRestaurant(name);
		Restaurant restaurant = optionalRestaurant.get();
		String url = mapCache.getMap(restaurant.getCoordinates());

		restaurant.setMapURL(url);

		System.out.println(restaurant.toString());

		return Optional.ofNullable(restaurant);

	}

	public void addComment(Comment comment) {

		restaurantRepo.addComment(comment);

	}

}
