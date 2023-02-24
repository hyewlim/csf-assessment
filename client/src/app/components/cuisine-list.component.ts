import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {RestaurantService} from "../restaurant-service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-cuisine-list',
  templateUrl: './cuisine-list.component.html',
  styleUrls: ['./cuisine-list.component.css']
})
export class CuisineListComponent implements OnInit, OnDestroy{

  cuisines!: string[]

  cuisineSub$!: Subscription

  chosenCuisine!: string

  constructor(private restService: RestaurantService,
              private route: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {

    this.restService.getCuisineList()
    this.cuisineSub$ = this.restService.cuisines.subscribe(
      (result) => {
        this.cuisines = result
      }
    )

  }

  ngOnDestroy(): void {
    this.cuisineSub$.unsubscribe();
  }

  pickCuisine(i: number) {
      this.chosenCuisine = this.cuisines[i]
      console.log(this.chosenCuisine)
      this.restService.getRestaurantsByCuisine(this.chosenCuisine)
      this.route.navigate(['cuisine'])
  }
}
