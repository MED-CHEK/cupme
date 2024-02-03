import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { DomSanitizer } from '@angular/platform-browser';
import { ProtocolCartDTO } from '../../entities/protocol.model';
import { CartItemDisplayDTO } from 'app/entities/cartItem.model';
import { CartService } from 'app/cart/cart.service';
import { ToastService } from '../toast/toast.service';

@Component({
  selector: 'jhi-proto',
  templateUrl: './proto.component.html',
  styleUrls: ['./proto.component.scss'],
})
export class ProtoComponent implements OnInit {
  @Input()
  protocol!: ProtocolCartDTO;

  imagePath!: string;

  constructor(
    private router: Router,
    private sanitizer: DomSanitizer,
    private cartService: CartService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.getProtoImage();

    /*  this.route.queryParams.pipe(mergeMap(params => this.detailService.get(params.key))).subscribe({
      next: () => (this.success = true),
      error: () => (this.error = true),
    }); */
  }

  getProtoImage() {
    let path = this.protocol.picture.file;

    if (path == undefined) {
      this.imagePath = '../../../../content/images/Pictos/No-picture.svg';
    } else {
      this.imagePath = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + path) as string;
    }
  }

  selectProtocol(protocol: any) {
    this.router.navigate(['/protocols', protocol.id]);
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
}
