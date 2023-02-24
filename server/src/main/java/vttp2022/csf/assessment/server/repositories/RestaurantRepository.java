package vttp2022.csf.assessment.server.repositories;

import java.util.*;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Repository;
import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.LatLng;
import vttp2022.csf.assessment.server.models.Restaurant;

@Repository
public class RestaurantRepository {
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<String> getCuisines() {
		//	db.getCollection("restaurants").distinct("cuisine")
		List<String> cuisines = mongoTemplate.findDistinct(
				new Query(), "cuisine", "restaurants", String.class);

		return cuisines;
	}

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

	public Optional<Restaurant> getRestaurant(String name) {

//		db.restaurants.aggregate([
//				{
//						$match: {
//			name: "The Malt House",
//		}
//   },
//		{
//			$project: {
//				restaurant_id: 1,
//						name: 1,
//						cuisine: 1,
//						address: {
//					$concat: ["$address.building", ", ", "$address.street", ", ", "$address.zipcode", ", ", "$address.borough"]
//				},
//				coordinates: "$address.coord"
//			}
//		}
//])



		Query query = Query.query(Criteria.where("name").regex(name, "i"));
		Aggregation aggregation = Aggregation.newAggregation(
				Aggregation.match(
						Criteria.where("name").is(name)
				),
				Aggregation.project("restaurant_id", "name", "cuisine")
						.and(StringOperators.Concat.valueOf("$address.building")
										.concat(",")
										.concatValueOf("$address.street")
										.concat(",")
										.concatValueOf("$address.zipcode")
										.concat(",")
										.concatValueOf("$borough")
										).as("address")
										.and("address.coord").as("coordinates")
		);

		 AggregationResults<Document> result = mongoTemplate.aggregate(aggregation, "restaurants", Document.class);
		 List<Document> docs = result.getMappedResults();
		Restaurant restaurant = new Restaurant();
		LatLng latLng = new LatLng();
		 for (Document doc: docs){
			 restaurant.setAddress(doc.getString("address"));
			 restaurant.setCuisine(doc.getString("cuisine"));
			 restaurant.setName(doc.getString("name"));
			 restaurant.setRestaurantId(doc.getString("restaurant_id"));

			 List<Double> coor = doc.getList("coordinates", Double.class);
			 latLng.setLatitude(coor.get(0).floatValue());
			 latLng.setLongitude(coor.get(1).floatValue());
			 restaurant.setCoordinates(latLng);
		 }
		 return Optional.ofNullable(restaurant);

	}

	public void addComment(Comment comment) {

		mongoTemplate.insert(comment, "comments");

	}

}
