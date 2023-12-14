import { Component } from '@angular/core';

@Component({
  selector: 'ngbd-rating-decimal',
  templateUrl: './rating-decimal.html',
  styles: [
    `
      .star {
        position: relative;
        display: inline-block;
        font-size: 2rem;
        color: #fff6f6;
      }
      .full {
        color: red;
      }
      .half {
        position: absolute;
        display: inline-block;
        overflow: hidden;
        color: goldenrod;
      }
    `,
  ],
})
export class NgbdRatingDecimal {
  currentRate = 3.4;
}
