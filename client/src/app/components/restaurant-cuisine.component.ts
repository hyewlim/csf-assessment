import {Component, OnDestroy, OnInit} from '@angular/core';
import {RestaurantService} from "../restaurant-service";
import {Subscription} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-restaurant-cuisine',
  templateUrl: './restaurant-cuisine.component.html',
  styleUrls: ['./restaurant-cuisine.component.css']
})
export class RestaurantCuisineComponent implements OnInit, OnDestroy{

  restaurants!: string[]

  chosenCuisine!: string

  restaurantSub$!: Subscription

  chosenCuisineSub$!: Subscription

  chosenRestaurant!: string;

  constructor(private restService: RestaurantService,
              private route: Router) {
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
    this.chosenRestaurant = this.restaurants[i]
    console.log("chosen rest", this.chosenRestaurant)
    this.restService.getRestaurant(this.chosenRestaurant)
      .then(result => {
        this.restService.chosenRestaurantSvc.next(result);
      })
    this.route.navigate(['restaurant'])
  }
}
