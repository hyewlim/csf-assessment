package vttp2022.csf.assessment.server.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;

@Repository
public class RestaurantRepository {
	@Autowired
	private MongoTemplate mongoTemplate;

	// TODO Task 2
	// Use this method to retrive a list of cuisines from the restaurant collection
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	//  
	public List<String> getCuisines() {
		//	db.getCollection("restaurants").distinct("cuisine")
		List<String> cuisines = mongoTemplate.findDistinct(
				new Query(), "cuisine", "restaurants", String.class);

		return cuisines;
	}

	// TODO Task 3
	// Use this method to retrive a all restaurants for a particular cuisine
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	//  
	public List<String> getRestaurantsByCuisine(String cuisine) {
		//db.getCollection("restaurants").find({cuisine: "Armenian"})
		Query query = Query.query(Criteria.where("cuisine").regex(cuisine, "i"));
		query.fields().include("name").exclude("_id");
		List<Document> documents = mongoTemplate.find(query, Document.class, "restaurants");
		List<String> listOfRest = new LinkedList<>();

		for (Document d: documents){
			listOfRest.add(d.getString("name"));
		}

		return listOfRest;

	}

	// TODO Task 4
	// Use this method to find a specific restaurant
	// You can add any parameters (if any) 
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	//  
	public Optional<Restaurant> getRestaurant(String name) {

		//db.getCollection("restaurants").find({name: "The Malt House"})
		Query query = Query.query(Criteria.where("name").regex(name, "i"));
		query.fields()
				.include("name")
				.include("cuisine")
				.include("address")
				.include("borough")
				.include("coordinates")
				.exclude("_id");
		List<Document> documents = mongoTemplate.find(query, Document.class, "restaurants");
		List<String> listOfRest = new LinkedList<>();
		return null;

//		List<Restaurant> result = new LinkedList<>();
//		for (Document rest: restaurants){
//			Restaurant restaurant = new Restaurant();
//			restaurant.setRestaurantId(rest.getString("restaurant_id"));
//			restaurant.setCuisine(rest.getString("cuisine"));
//			restaurant.setName(rest.getString("name"));
//			restaurant.setAddress(rest.getString());
	}

	// TODO Task 5
	// Use this method to insert a comment into the restaurant database
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	//  
	public void addComment(Comment comment) {
		// Implmementation in here
		
	}
	
	// You may add other methods to this class

}
