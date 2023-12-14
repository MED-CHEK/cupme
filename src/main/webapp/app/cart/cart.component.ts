import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { CartItemDisplayDTO, CartItemDTO } from '../entities/cartItem.model';
import { CartService } from './cart.service';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { OrderService } from '../order/order.service';

@Component({
  selector: 'jhi-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
})
export class CartComponent implements OnInit {
  cartItems: CartItemDisplayDTO[] = [];
  subTotal: number = 0;
  transactionId: string = '';
  transactionCompleted: boolean = false;
  screenWidth!: number;
  isMobileDisplay!: boolean;

  @ViewChild('paymentRef', { static: true }) paymentRef!: ElementRef;

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private sanitizer: DomSanitizer,
    private router: Router
  ) {
    this.cartService.loadCartFromLocalStorage();
    this.cartItems = this.cartService.getCartItems();
    this.getSubTotal();
  }

  ngOnInit(): void {
    this.onWindowResize();
    window.paypal
      .Buttons({
        createOrder: (data: any, actions: any) => {
          return actions.order.create({
            purchase_units: [
              {
                description: 'Commande sur le site de la boutique',
                amount: {
                  currency_code: 'EUR',
                  value: this.subTotal.toFixed(2),
                  breakdown: {
                    item_total: {
                      currency_code: 'EUR',
                      value: this.subTotal.toFixed(2),
                    },
                  },
                },
                items: this.cartItems.map(item => {
                  return {
                    name: item.name,
                    quantity: item.quantity,
                    category: 'PHYSICAL_GOODS',
                    unit_amount: {
                      currency_code: 'EUR',
                      value: item.price,
                    },
                  };
                }),
              },
            ],
          });
        },
        onApprove: async (data: any, actions: any) => {
          const order = await actions.order.capture().then((details: any) => {
            if (details.status == 'COMPLETED') {
              this.cartService.transactionId = details.id;
              this.orderService.createOrder(this.cartItems, details.id).subscribe(res => {
                this.router.navigate(['/success']);
                this.clearCart();
              });
            }
          });
        },
        onError: (err: any) => {
          console.log(err);
        },
      })
      .render(this.paymentRef.nativeElement);
  }

  @HostListener('window:resize', ['$event'])
  onWindowResize() {
    this.screenWidth = window.innerWidth;
    if (this.screenWidth < 768) {
      this.isMobileDisplay = true;
    } else {
      this.isMobileDisplay = false;
    }
  }

  clearCart(): void {
    this.cartService.clearCart();
    this.cartItems = [];
    this.getSubTotal();
  }

  addToCart(product: any) {
    this.cartService.addToCart(product);
    this.getSubTotal();
  }

  removeFromCart(product: any) {
    this.cartService.removeFromCart(product);
    this.getSubTotal();
  }

  increaseQuantity(product: any) {
    this.cartService.increaseQuantity(product);
    this.getSubTotal();
  }

  reduceQuantity(product: any) {
    this.cartService.reduceQuantity(product);
    this.getSubTotal();
  }

  getSubTotal(): number {
    this.subTotal = 0;
    this.cartItems.forEach(item => {
      this.subTotal += item.quantity * item.price;
    });
    return this.subTotal;
  }

  getProtoImage(protocol: any): string {
    let path = protocol.picture.file;
    let imagePath!: string;
    if (path == undefined) {
      imagePath = '../../../../content/images/Pictos/No-picture.svg';
    } else {
      imagePath = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + path) as string;
    }
    return imagePath;
  }

  getProductImage(product: any): string {
    let path = product.picture.file;
    console.log(product.picture);
    let imagePath!: string;
    if (path == undefined) {
      imagePath = '../../../../content/images/Pictos/No-picture.svg';
    } else {
      imagePath = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + path) as string;
    }
    return imagePath;
  }
}
