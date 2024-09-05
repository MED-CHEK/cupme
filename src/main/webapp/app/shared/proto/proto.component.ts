import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { ProtocolCartDTO } from '../../entities/protocol.model';
import { CartItemDisplayDTO } from 'app/entities/cartItem.model';
import { CartService } from 'app/cart/cart.service';
import { ToastService } from '../toast/toast.service';
import { ProductType } from 'app/entities/product-type.enum';

@Component({
  selector: 'jhi-proto',
  templateUrl: './proto.component.html',
  styleUrls: ['./proto.component.scss'],
})
export class ProtoComponent implements OnInit {
  @Input()
  protocol!: ProtocolCartDTO;

  imagePath!: string;
  type = ProductType;

  constructor(private router: Router, private cartService: CartService, private toastService: ToastService) {}

  ngOnInit(): void {
    /*  this.route.queryParams.pipe(mergeMap(params => this.detailService.get(params.key))).subscribe({
      next: () => (this.success = true),
      error: () => (this.error = true),
    }); */
  }

  selectProtocol(protocol: any) {
    this.router.navigate(['/protocols', protocol.id]);
  }

  addProtocolToCart(protocol: ProtocolCartDTO) {
    const cartItem: CartItemDisplayDTO = {
      productId: protocol.id,
      name: protocol.name,
      price: protocol.price,
      picture: ('content/images/' + protocol.id + '/' + protocol.picture.name) as string,
      type: this.type.PROTOCOL,
      createdDate: new Date().toISOString(),
      quantity: 1,
    };
    const isNewItem = this.cartService.addToCart(cartItem);
    if (isNewItem) {
      this.toastService.show('Protocol added to cart', { classname: 'bg-success text-light', delay: 2000 });
    } else {
      this.toastService.show('Protocol already in cart', { delay: 2000 });
    }
  }
}
