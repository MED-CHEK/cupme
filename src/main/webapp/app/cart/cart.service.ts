import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from '../core/config/application-config.service';
import { CartItemDisplayDTO } from '../entities/cartItem.model';
import { LocalStorageService } from 'ngx-webstorage';
import { EventEmitter } from '@angular/core';
import { Observable, from } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class CartService {
  cartdata = new EventEmitter<CartItemDisplayDTO[] | []>();
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
    console.log(item);
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
    this.cartdata.emit(this.cartItems);
  }

  persistCart(): void {
    const cartItemSession = this.cartItems.map(item => {
      return {
        productId: item.productId,
        quantity: item.quantity,
        createdDate: item.createdDate,
        type: item.protocol ? 'PROTOCOL' : 'PRODUCT',
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
        this.cartdata.emit(this.cartItems);
        this.saveCartToLocalStorage();
      });
  }

  /* setCart(cart: string[]): Observable<void> {
    // Retourne un observable à partir de la sauvegarde en local storage
    return from(new Promise<void>((resolve) => {
      localStorage.setItem('cart-itesm', JSON.stringify(cart));
      resolve();
    }));
  } */

  /* getCartDTO(): Observable<CartDTO> {
    return this.http.get<CartDTO>(this.applicationConfigService.getEndpointFor('api/cart'));
  }

  setToEmpty(CartDTOId: number): Observable<any> {
    return this.http.delete(this.applicationConfigService.getEndpointFor('api/cartItems/' + CartDTOId));
  }

  getCartItems(): Observable<CartItemDTO[]> {
    return this.http.get<CartItemDTO[]>(this.applicationConfigService.getEndpointFor('api/cartItems'));
  } 

  addCartItem(cartItemDTO: CartItemDTO): Observable<any> {
    return this.http.post<CartItemDTO>(this.applicationConfigService.getEndpointFor('api/cartItems'), cartItemDTO);
  }

  updateCartItem(cartItemDTO: CartItemDTO): Observable<any> {
    return this.http.put<CartItemDTO>(this.applicationConfigService.getEndpointFor('api/cartItems'), cartItemDTO);
  }

  deleteCartItem(id: number): Observable<number> {
    const size = this.http.delete<number>(this.applicationConfigService.getEndpointFor('api/cartItem/' + id));
    size.subscribe((cartSize: number) => {
      this.setCartSize(cartSize);
    });
    return size;
  }
} */
}
