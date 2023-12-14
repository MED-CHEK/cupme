import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { ProtocolCartDTO } from '../../entities/protocol.model';
import { OrderService } from '../../order/order.service';

@Component({
  selector: 'jhi-my-result',
  templateUrl: './myresult.component.html',
  styleUrls: ['./myresult.component.scss'],
})
export class MyResultComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  protocols: ProtocolCartDTO[] = [];

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router, private orderservice: OrderService) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));

    this.orderservice.getOrderProtocols().subscribe(protocols => {
      this.protocols = protocols;
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  selectProtocol(protocol: any) {
    this.router.navigate(['/myprotocols', protocol.id]);
  }
}
