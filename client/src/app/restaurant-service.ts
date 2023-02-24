import { Restaurant, Comment } from './models'
import {HttpClient} from "@angular/common/http";
import {lastValueFrom, Subject} from "rxjs";
import {Injectable, Output} from "@angular/core";

@Injectable()
export class RestaurantService {

  cuisines = new Subject<string[]>()

  restaurants = new Subject<string[]>()

  chosenCuisineSvc = new Subject<string>()

  constructor(private http: HttpClient) {
  }

	// TODO Task 2
	// Use the following method to get a list of cuisines
	// You can add any parameters (if any) and the return type
	// DO NOT CHNAGE THE METHOD'S NAME
	public getCuisineList() {

    return lastValueFrom(this.http.get<string[]>("/api/cuisines"))
      .then(result => {
        this.cuisines.next(result)
      })
      .catch(error => {
        console.error(error)
      })



	}

	// TODO Task 3
	// Use the following method to get a list of restaurants by cuisine
	// You can add any parameters (if any) and the return type
	// DO NOT CHNAGE THE METHOD'S NAME
	public getRestaurantsByCuisine(cuisine: string) {
		// Implememntation in here

    lastValueFrom(this.http.get<string[]>("/api/" + cuisine + "/restaurants"))
      .then(result => {
        console.log(result)
        this.restaurants.next(result)
        this.chosenCuisineSvc.next(cuisine)
      })
      .catch(error => {
        console.error(error)
      })



	}

	// TODO Task 4
	// Use this method to find a specific restaurant
	// You can add any parameters (if any)
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// public getRestaurant(??): Promise<Restaurant> {
	// 	// Implememntation in here
  //
  //
	// }

	// TODO Task 5
	// Use this method to submit a comment
	// DO NOT CHANGE THE METHOD'S NAME OR SIGNATURE
	// public postComment(comment: Comment): Promise<any> {
	// 	// Implememntation in here
  //
	// }
}
