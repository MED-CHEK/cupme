import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { CartService } from '../../cart/cart.service';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  inProductDTOion?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];
  cartSize!: number;

  constructor(
    private loginService: LoginService,
    private cartService: CartService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.entitiesNavbarItems = EntityNavbarItems;

    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
    let cartItems = localStorage.getItem('jhi-cart-items');
    if (cartItems) {
      this.cartSize = JSON.parse(cartItems).length;
    } else {
      this.cartSize = 0;
    }
    this.loadCart();
  }

  loadCart(): void {
    this.cartService.loadCartFromLocalStorage();
    //this.cartSize = this.cartService.getCartItems().length;
    this.cartService.cartdata.subscribe(items => {
      this.cartSize = items.length;
    });
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorageService.store('locale', languageKey);
    this.translateService.use(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.collapseNavbar();
    this.cartService.persistCart();
    this.loginService.logout();
    this.router.navigate(['/login']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }
}
