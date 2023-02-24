import {Component, OnDestroy, OnInit} from '@angular/core';
import {Comment, Restaurant} from "../models";
import {RestaurantService} from "../restaurant-service";
import {Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-restaurant-details',
  templateUrl: './restaurant-details.component.html',
  styleUrls: ['./restaurant-details.component.css']
})
export class RestaurantDetailsComponent implements OnInit, OnDestroy{

  form!: FormGroup;

  commentToSubmit!: Comment;

  restaurant!: Restaurant;

  restaurantSub$!: Subscription;
	// TODO Task 4 and Task 5
	// For View 3

  constructor(private restService: RestaurantService,
              private fb: FormBuilder,
              private route: Router) {
  }

  ngOnInit(): void {

    this.form = this.fb.group({
      name: this.fb.control<string>("", Validators.min(4)),
      rating: this.fb.control<number>(1,
        [Validators.min(1), Validators.max(5)]),
      comment: this.fb.control<string>("", Validators.required)
    })

    this.restaurantSub$ = this.restService.chosenRestaurantSvc.subscribe(
      (result) => {
        this.restaurant = result;
      }
    )

  }

  ngOnDestroy(): void {

    this.restaurantSub$.unsubscribe();
  }


  submitForm() {
    this.commentToSubmit = this.form.value
    this.commentToSubmit.restaurantId = this.restaurant.restaurantId
    this.restService.postComment(this.commentToSubmit);

  }

  return() {
    this.route.navigate(['cuisine'])
  }
}
