import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { CartService } from '../../cart/cart.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { createCustomDateValidator } from 'app/shared/date/date/customDateValidator';
import { SessionService } from 'app/services/session.service';
import { AppointmentDTO, ContactType, SessionDTO } from 'app/entities/session.model';
import { CartItemDisplayDTO } from 'app/entities/cartItem.model';
import { ProductType } from 'app/entities/product-type.enum';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastService } from 'app/shared/toast/toast.service';

const dateTemplate = {
  year: 0,
  month: 0,
  day: 0,
};
@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];
  cartSize!: number;
  screenWidth!: number;
  isMobileDisplay!: boolean;
  onlineSessions: SessionDTO[] = [];
  selectedSession!: SessionDTO;
  type = ContactType;
  appointmentForm = new FormGroup({
    id: new FormControl(0),
    email: new FormControl('', [Validators.required, Validators.email]),
    telephone: new FormControl('', [
      Validators.required,
      Validators.minLength(10),
      Validators.maxLength(10),
      Validators.pattern('^[0-9]*$'),
    ]),
    type: new FormControl('', [Validators.required]),
    sessionDate: new FormControl(dateTemplate, [createCustomDateValidator()]),
    sessionTime: new FormControl('', [Validators.required]),
  });
  productType = ProductType;
  modalRef: any;

  constructor(
    private loginService: LoginService,
    private cartService: CartService,
    private sessionService: SessionService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private router: Router,
    private modalService: NgbModal,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.onWindowResize();
    this.entitiesNavbarItems = EntityNavbarItems;
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
      this.loadSession();
    });

    let cartItems = localStorage.getItem('jhi-cart-items');
    if (cartItems) {
      this.cartSize = JSON.parse(cartItems).length;
    } else {
      this.cartSize = 0;
    }

    this.loadCart();
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

  loadCart(): void {
    this.cartService.loadCartFromLocalStorage();
    this.cartService.cartdata$.subscribe({
      next: (items: CartItemDisplayDTO[]) => {
        this.cartSize = items.length;
      },
      error: () => console.log('Error in subscription'),
    });
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorageService.store('locale', languageKey);
    this.translateService.use(languageKey);
  }

  loadSession(): void {
    this.sessionService.query().subscribe(sessions => {
      this.onlineSessions = sessions;
    });
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

  validateAppointment(): void {
    this.appointmentForm.markAllAsTouched();
    if (this.appointmentForm.getRawValue().type === ContactType.MAIL) {
      this.appointmentForm.controls['email'].addValidators([Validators.required]);

      this.appointmentForm.controls['telephone'].clearValidators();

      this.appointmentForm.controls['email'].updateValueAndValidity();
      this.appointmentForm.controls['telephone'].updateValueAndValidity();
    } else {
      this.appointmentForm.controls['telephone'].addValidators([Validators.required]);

      this.appointmentForm.controls['email'].clearValidators();

      this.appointmentForm.controls['email'].updateValueAndValidity();
      this.appointmentForm.controls['telephone'].updateValueAndValidity();
    }
  }

  schedule(session: SessionDTO, content: any): void {
    this.selectedSession = session;
    this.modalService.open(content, { modalDialogClass: 'session-modal', centered: true });
  }

  addToCart() {
    this.validateAppointment();

    if (this.appointmentForm.valid) {
      const appointmentFormValue = this.appointmentForm?.getRawValue();
      const appointment: AppointmentDTO = {
        id: appointmentFormValue.id ?? 0,
        email: appointmentFormValue.email ?? '',
        telephone: appointmentFormValue.telephone ?? '',
        type: appointmentFormValue.type as ContactType,
        session: this.selectedSession,
        appointmentDate: appointmentFormValue.sessionDate
          ? new Date(
              appointmentFormValue.sessionDate.year,
              appointmentFormValue.sessionDate.month - 1,
              appointmentFormValue.sessionDate.day
            )
              .toISOString()
              .split('T')[0]
          : null,
        appointmentTime: this.appointmentForm?.get('sessionTime')?.value ?? '',
      };

      const cartItem: CartItemDisplayDTO = {
        productId: appointment.session.id ? appointment.session.id : 0,
        name: appointment.session.name,
        price: appointment.session.price,
        picture: '../../content/images/main.png',
        type: this.productType.SESSION,
        createdDate: new Date().toISOString(),
        quantity: 1,
        appointmentInfo: {
          email: appointment.type === this.type.MAIL ? appointment.email : '',
          telephone: appointment.type === this.type.WHATSAPP ? appointment.telephone : '',
          type: appointment.type,
          appointmentDate: appointment.appointmentDate ?? '',
          appointmentTime: appointment.appointmentTime,
        },
      };
      const isNewItem = this.cartService.addToCart(cartItem);
      if (isNewItem) {
        this.toastService.show('Product added to cart', { classname: 'bg-success text-light', delay: 2000 });
      } else {
        this.toastService.show('Product already in cart', { delay: 2000 });
      }
    }

    this.appointmentForm.reset();
    this.modalService.dismissAll();
  }
}
