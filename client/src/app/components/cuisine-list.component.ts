import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {RestaurantService} from "../restaurant-service";

@Component({
  selector: 'app-cuisine-list',
  templateUrl: './cuisine-list.component.html',
  styleUrls: ['./cuisine-list.component.css']
})
export class CuisineListComponent implements OnInit, OnDestroy{

	// TODO Task 2
	// For View 1

  cruisines!: string[]

  cruisineSub$!: Subscription

  constructor(private restService: RestaurantService) {
  }

  ngOnInit(): void {

    this.restService.getCuisineList()
    this.cruisineSub$ = this.restService.crusines.subscribe(
      (result) => {
        this.cruisines = result
      }
    )
    //
    // this.restService.getCuisineList().then(
    //   (result) => {
    //
    //     this.cruisines = result;
    //   }
    // )

    //  this.restService.crusines.subscribe(
    //   result => {
    //     this.cruisines = result;
    //   }
    // )
  }

  ngOnDestroy(): void {
  }





}
