import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ProtocolCartDTO, ProtocolDetailDTO, ProtocolDTO } from '../../entities/protocol.model';
import { DomSanitizer } from '@angular/platform-browser';
import { CartService } from '../../cart/cart.service';
import { CartDTO } from '../../entities/cart.model';
import { CartItemDisplayDTO, CartItemDTO } from '../../entities/cartItem.model';
import { ProductCartDTO, ProductDTO } from '../../entities/product.model';
import { ProtocolService } from '../protocol.service';
import { ToastService } from '../../shared/toast/toast.service';

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

  constructor(
    private protocolService: ProtocolService,
    private cartService: CartService,
    private route: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      this.protocolService.getProtocol(id).subscribe(protocol => {
        this.protocol = protocol;
        this.isLoading = false;
        this.getAssetImage();
      });
    });
  }

  getAssetImage() {
    let path = this.protocol.picture.file;

    if (path == undefined) {
      this.imagePath = '../../../../assets/images/Pictos/No-picture.svg';
    } else {
      this.imagePath = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + path) as string;
    }
  }

  addProductToCart(product: ProductCartDTO) {
    const cartItem: CartItemDisplayDTO = {
      productId: product.id ? product.id : 0,
      name: product.name,
      price: product.price,
      picture: (('../../content/images/' + product.id + '/' + product.picture.name) as string) + '.png',
      protocol: false,
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

  addProtocolToCart(protocol: ProtocolCartDTO) {
    const cartItem: CartItemDisplayDTO = {
      productId: protocol.id,
      name: protocol.name,
      price: protocol.price,
      picture: (('../../content/images/' + protocol.id + '/' + protocol.picture.name) as string) + '.png',
      protocol: true,
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

  getProductImage(product: ProductCartDTO): string {
    let path = product.picture.file;
    let imagePath!: string;
    if (path == undefined) {
      imagePath = '../../../../content/images/Pictos/No-picture.svg';
    } else {
      imagePath = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + path) as string;
    }
    return imagePath;
  }
}
