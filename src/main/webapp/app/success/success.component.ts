import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart/cart.service';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.scss'],
})
export class SuccessComponent implements OnInit {
  transactionId: string = '';

  constructor(private cartService: CartService, private router: Router) {}

  ngOnInit(): void {
    this.transactionId = this.cartService.transactionId;
  }
  /* 
  goBackToHome(): void {
    this.router.navigate([''], { replaceUrl: true });
  } */
}
