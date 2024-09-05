import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from '../core/config/application-config.service';
import { CartItemDisplayDTO } from '../entities/cartItem.model';
import { LocalStorageService } from 'ngx-webstorage';
import { EventEmitter } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class CartService {
  private cartdata = new BehaviorSubject<CartItemDisplayDTO[]>([]);
  public cartdata$ = this.cartdata.asObservable();
  private cartItems: CartItemDisplayDTO[] = [];
  transactionId: string = '';

  constructor(
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService,
    private localStorageService: LocalStorageService
  ) {}

  getCartItems(): any[] {
    return this.cartItems;
  }

  addToCart(item: any): boolean {
    let isNewItem = false;
    const existingItem = this.cartItems.find(i => i.productId === item.productId);

    if (!existingItem) {
      item.quantity = item.isProtocol ? 1 : item.quantity++;
      this.cartItems.push(item);
      isNewItem = true;
    } else {
      isNewItem = false;
    }

    this.saveCartToLocalStorage();
    return isNewItem;
  }

  removeFromCart(item: any): void {
    const existingItem = this.cartItems.find(i => i.productId === item.productId);

    if (existingItem) {
      this.cartItems.splice(this.cartItems.indexOf(item), 1);
    }

    this.saveCartToLocalStorage();
  }

  increaseQuantity(item: any) {
    const existingItem = this.cartItems.find(i => i.productId === item.productId);

    if (existingItem) {
      // Si l'élément existe déjà dans le panier, augmentez simplement la quantité
      existingItem.quantity ? existingItem.quantity++ : (existingItem.quantity = 1);
    }
    this.saveCartToLocalStorage();
  }

  reduceQuantity(item: any) {
    const existingItem = this.cartItems.find(i => i.productId === item.productId);

    if (existingItem) {
      // Si l'élément existe déjà dans le panier, augmentez simplement la quantité
      existingItem.quantity--;
      if (existingItem.quantity <= 0) {
        this.cartItems.splice(this.cartItems.indexOf(item), 1);
      }
    }

    this.saveCartToLocalStorage();
  }

  clearCart(): void {
    this.cartItems = [];
    this.saveCartToLocalStorage();
  }

  saveCartToLocalStorage(): void {
    this.localStorageService.store('cart-items', this.cartItems);
    this.loadCartFromLocalStorage();
  }

  loadCartFromLocalStorage(): void {
    this.cartItems = this.localStorageService.retrieve('cart-items') || [];
    console.log('Updating cart data:', this.cartItems);
    this.cartdata.next(this.cartItems);
  }

  persistCart(): void {
    const cartItemSession = this.cartItems.map(item => {
      return {
        productId: item.productId,
        quantity: item.quantity,
        createdDate: item.createdDate,
        type: item.type,
      };
    });

    this.http.post(this.applicationConfigService.getEndpointFor('api/cartItems/persist'), cartItemSession).subscribe();
    this.clearCart();
  }

  loadCartFromBD(): void {
    this.http
      .get<CartItemDisplayDTO[]>(this.applicationConfigService.getEndpointFor('api/cartItems/display'))
      .subscribe((cartItems: CartItemDisplayDTO[]) => {
        this.cartItems = cartItems;
        this.cartItems.forEach(item => {
          item.picture = item.picture.replace(/\\/g, '/');
        });
        this.cartdata.next(this.cartItems);
        this.saveCartToLocalStorage();
      });
  }
}
