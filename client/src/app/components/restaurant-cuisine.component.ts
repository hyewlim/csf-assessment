import {Component, OnDestroy, OnInit} from '@angular/core';
import {RestaurantService} from "../restaurant-service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-restaurant-cuisine',
  templateUrl: './restaurant-cuisine.component.html',
  styleUrls: ['./restaurant-cuisine.component.css']
})
export class RestaurantCuisineComponent implements OnInit, OnDestroy{

	// TODO Task 3
	// For View 2

  restaurants!: string[]

  chosenCuisine!: string

  restaurantSub$!: Subscription

  chosenCuisineSub$!: Subscription

  constructor(private restService: RestaurantService) {
  }

  ngOnInit(): void {

    this.chosenCuisineSub$ = this.restService.chosenCuisineSvc.subscribe(
      (result) => {
        this.chosenCuisine = result;
      }
    )

    this.restaurantSub$ = this.restService.restaurants.subscribe(
      (result) => {
        this.restaurants = result;
      }
    )

  }

  ngOnDestroy(): void {
    this.restaurantSub$.unsubscribe()
    this.chosenCuisineSub$.unsubscribe()
  }


  pickRestaurant(i: number) {

  }
}
