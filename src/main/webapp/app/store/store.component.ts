import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { CartService } from 'app/cart/cart.service';
import { CartItemDisplayDTO } from 'app/entities/cartItem.model';
import { ProductCartDTO } from 'app/entities/product.model';
import { ProtocolCartDTO } from 'app/entities/protocol.model';
import { ProtocolService } from 'app/protocol/protocol.service';
import { ProductService } from 'app/services/product.service';
import { ToastService } from 'app/shared/toast/toast.service';

@Component({
  selector: 'jhi-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.scss'],
})
export class StoreComponent implements OnInit {
  protocols: ProtocolCartDTO[] = [];
  products: ProductCartDTO[] = [];
  isProtocolsTab: boolean = true;
  isProtocolsLoading = true;
  isProductsLoading = true;

  constructor(
    private protocolService: ProtocolService,
    private productService: ProductService,
    private cartService: CartService,
    private sanitizer: DomSanitizer,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.protocolService.getProtocols().subscribe(protocols => {
      this.protocols = protocols;
      this.isProtocolsLoading = false;
    });

    this.productService.getProducts().subscribe(products => {
      this.products = products;
      this.isProductsLoading = false;
    });
  }

  selectTabs(tab: string) {
    this.isProtocolsTab = tab === 'protocols';
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
