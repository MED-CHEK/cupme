import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { CartItemDisplayDTO } from '../entities/cartItem.model';
import { CartService } from './cart.service';
import { Router } from '@angular/router';
import { OrderService } from '../order/order.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Address } from 'app/entities/address.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AddressService } from 'app/admin/user-management/service/address.service';
import { PromoCodeManagementService } from 'app/admin/promo-code-management/service/promo-code-management.service';
import { PromoCodeDTO } from 'app/entities/promo-code.model';
import { ProductType } from 'app/entities/product-type.enum';

const addressTemplate = {} as Address;

const newAddress: Address = {
  address: '',
  city: '',
  country: '',
  postalCode: '',
  type: '',
} as Address;
@Component({
  selector: 'jhi-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
})
export class CartComponent implements OnInit {
  isSaving = false;

  cartItems: CartItemDisplayDTO[] = [];
  account: Account | null = null;
  billingAddress: Address | null = null;
  shippingAddress: Address | null = null;
  addShippingAddress: boolean = false;
  addressForm = new FormGroup({
    address: new FormControl(addressTemplate.address, Validators.required),
    city: new FormControl(addressTemplate.city, Validators.required),
    country: new FormControl(addressTemplate.country, Validators.required),
    postalCode: new FormControl(addressTemplate.postalCode, Validators.required),
  });
  promoForm = new FormGroup({
    code: new FormControl('', Validators.required),
  });
  promoCode: PromoCodeDTO | null = null;
  promoCodeErrorNotExist = false;
  promoCodeErrorNotActivated = false;
  types = ['BILLING', 'SHIPPING'];
  subTotal: number = 0;
  subTotalWithoutPromo: number = 0;
  transactionId: string = '';
  transactionCompleted: boolean = false;
  screenWidth!: number;
  isMobileDisplay!: boolean;
  type = ProductType;

  @ViewChild('paymentRef', { static: true }) paymentRef!: ElementRef;

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private router: Router,
    private modalService: NgbModal,
    private accountService: AccountService,
    private addressService: AddressService,
    private promoCodeService: PromoCodeManagementService
  ) {}

  ngOnInit(): void {
    this.onWindowResize();

    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
      if (account?.addresses) {
        this.billingAddress = account.addresses.find((address: Address) => address.type === 'BILLING') || null;
        this.shippingAddress = account.addresses.find((address: Address) => address.type === 'SHIPPING') || null;
      } else {
        this.billingAddress = null;
        this.shippingAddress = null;
      }
    });

    this.cartService.cartdata$.subscribe({
      next: (items: CartItemDisplayDTO[]) => {
        this.cartItems = items;
        this.getSubTotal();
      },
      error: () => console.log('Error in subscription'),
    });

    window.paypal
      .Buttons({
        createOrder: (data: any, actions: any) => {
          return actions.order.create({
            purchase_units: [
              {
                description: "Commande sur le site Cup'me",
                amount: {
                  currency_code: 'EUR',
                  value: this.subTotal.toFixed(2),
                  breakdown: {
                    item_total: {
                      currency_code: 'EUR',
                      value: this.subTotalWithoutPromo.toFixed(2),
                    },
                    discount: {
                      currency_code: 'EUR',
                      value: (this.subTotalWithoutPromo - this.subTotal).toFixed(2),
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
    this.subTotalWithoutPromo = 0;

    this.cartItems.forEach(item => {
      this.subTotal += item.quantity * item.price;
      this.subTotalWithoutPromo = this.subTotal;
    });

    if (this.promoCode && this.promoCode.activated) {
      this.subTotal = this.subTotal - (this.subTotal * this.promoCode.discount) / 100;
    }
    return this.subTotal;
  }

  addAddress() {
    this.isSaving = true;

    const newAddress: Address = {
      id: this.shippingAddress?.id,
      type: 'SHIPPING',
      address: this.addressForm.get('address')?.value,
      city: this.addressForm.get('city')?.value,
      country: this.addressForm.get('country')?.value,
      postalCode: this.addressForm.get('postalCode')?.value,
    } as Address;

    if (newAddress.id !== null || newAddress.id !== '') {
      this.addressService.update(newAddress).subscribe({
        next: (address: Address) => this.onSaveSuccess(address),
        error: () => this.onSaveError(),
      });
    } else {
      this.addressService.create(newAddress).subscribe({
        next: (address: Address) => this.onSaveSuccess(address),
        error: () => this.onSaveError(),
      });
    }
  }

  private onSaveSuccess(address: Address): void {
    this.isSaving = false;
    this.shippingAddress = address;
    this.addShippingAddress = false;
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

  editAddress() {
    this.addressForm.patchValue({
      address: this.shippingAddress?.address,
      city: this.shippingAddress?.city,
      country: this.shippingAddress?.country,
      postalCode: this.shippingAddress?.postalCode,
    });
    this.addShippingAddress = true;
  }

  applyPromoCode() {
    this.subTotal = this.subTotalWithoutPromo;
    this.promoCodeErrorNotExist = false;
    this.promoCodeErrorNotActivated = false;
    let code = this.promoForm.get('code')?.value;
    if (code !== null && code !== undefined) {
      this.promoCodeService.findByCode(code).subscribe({
        next: promoCode => {
          this.promoCode = promoCode;
          if (promoCode.activated) {
            this.subTotal = this.subTotal - (this.subTotal * this.promoCode.discount) / 100;
          } else {
            this.promoCodeErrorNotActivated = true;
          }
        },
        error: () => {
          this.promoCodeErrorNotExist = true;
        },
      });
    }
  }

  showCGV(content: any) {
    this.modalService.open(content, { modalDialogClass: 'cgv-modal', centered: true }).result.then(
      result => {},
      reason => {}
    );
  }
}
