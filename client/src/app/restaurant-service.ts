import { Restaurant, Comment } from './models'
import {HttpClient, HttpParams} from "@angular/common/http";
import {lastValueFrom, Subject} from "rxjs";
import {Injectable, Output} from "@angular/core";

@Injectable()
export class RestaurantService {

  cuisines = new Subject<string[]>()

  restaurants = new Subject<string[]>()

  chosenCuisineSvc = new Subject<string>()

  chosenRestaurantSvc = new Subject<Restaurant>()

  mapUrl = new Subject<string>()

  constructor(private http: HttpClient) {
  }

	public getCuisineList() {

    return lastValueFrom(this.http.get<string[]>("/api/cuisines"))
      .then(result => {
        this.cuisines.next(result)
      })
      .catch(error => {
        console.error(error)
      })

	}

	public getRestaurantsByCuisine(cuisine: string) {

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

	public getRestaurant(name: string): Promise<Restaurant> {

    let queryParams = new HttpParams()
      .set('name', name)

    return lastValueFrom(this.http
      .get<Restaurant>("/api/restaurant", {params: queryParams}))

  }

	public postComment(comment: Comment): Promise<any> {

    return lastValueFrom(this.http.post("/api/comments", comment))

	 }
}
