import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { ProtocolDTO } from 'app/entities/protocol.model';
import { ProtocolService } from '../protocol.service';
import { ProtocolCartDTO } from '../../entities/protocol.model';

@Component({
  selector: 'jhi-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.scss'],
})
export class ResultComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  protocols: ProtocolCartDTO[] = [];

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router, private protocolService: ProtocolService) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));

    this.protocolService.getProtocols().subscribe(protocols => {
      this.protocols = protocols;
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  selectProtocol(protocol: any) {
    this.router.navigate(['/protocols', protocol.id]);
  }
}
