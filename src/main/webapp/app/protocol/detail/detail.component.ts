import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ProtocolDetailDTO } from '../../entities/protocol.model';
import { CartService } from '../../cart/cart.service';
import { CartDTO } from '../../entities/cart.model';
import { CartItemDisplayDTO, CartItemDTO } from '../../entities/cartItem.model';
import { ProductCartDTO } from '../../entities/product.model';
import { ProtocolService } from '../protocol.service';
import { ToastService } from '../../shared/toast/toast.service';
import { ProductType } from 'app/entities/product-type.enum';

@Component({
  selector: 'jhi-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss'],
})
export class DetailComponent implements OnInit {
  protocol!: ProtocolDetailDTO;
  cart!: CartDTO;
  cartProductItem!: CartItemDTO;
  cartProtocolItem!: CartItemDTO;

  imagePath!: string;
  rating = 4.2;
  isLoading = true;
  show = false;
  type = ProductType;

  constructor(
    private protocolService: ProtocolService,
    private cartService: CartService,
    private route: ActivatedRoute,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      this.protocolService.getProtocol(id).subscribe(protocol => {
        this.protocol = protocol;
        this.isLoading = false;
        this.imagePath =
          this.protocol.pictures.find(picture => picture.main === true)?.file ?? '../../../../assets/images/Pictos/No-picture.svg';
      });
    });
  }

  addProductToCart(product: ProductCartDTO) {
    const cartItem: CartItemDisplayDTO = {
      productId: product.id ? product.id : 0,
      name: product.name,
      price: product.price,
      picture: ('content/images/' + product.id + '/' + product.picture.name) as string,
      type: this.type.PRODUCT,
      createdDate: new Date().toISOString(),
      quantity: 1,
    };
    const isNewItem = this.cartService.addToCart(cartItem);
    if (isNewItem) {
      this.toastService.show('Product added to cart', { classname: 'bg-success text-light', delay: 2000 });
    } else {
      this.toastService.show('Product already in cart', { delay: 2000 });
    }
  }

  addProtocolToCart(protocol: ProtocolDetailDTO) {
    const cartItem: CartItemDisplayDTO = {
      productId: protocol.id,
      name: protocol.name,
      price: protocol.price,
      picture: ('content/images/' + protocol.id + '/' + protocol.pictures.find(picture => picture.main === true)?.name) as string,
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
