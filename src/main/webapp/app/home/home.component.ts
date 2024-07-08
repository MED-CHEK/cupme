import { Component, OnInit, OnDestroy, HostListener, ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { ProtocolService } from 'app/protocol/protocol.service';
import { ProtocolCartDTO } from 'app/entities/protocol.model';
import { CarrouselImageService } from 'app/services/carrousel-image.service';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  screenWidth!: number;
  isMobileDisplay!: boolean;
  protocols: ProtocolCartDTO[] = [];
  images: String[] = [];
  json!: String;
  @ViewChild('elementToScrollTo') elementToScrollTo!: ElementRef;

  private readonly destroy$ = new Subject<void>();

  constructor(
    private accountService: AccountService,
    private router: Router,
    private protocolService: ProtocolService,
    private carrouselImageService: CarrouselImageService
  ) {}

  ngOnInit(): void {
    this.onWindowResize();
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));

    this.protocolService.getGenericProtocols().subscribe(protocols => (this.protocols = protocols));
    this.loadImages();
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
  scrollToElement(): void {
    window.scrollTo({ top: this.elementToScrollTo.nativeElement.getBoundingClientRect().top - 32, behavior: 'smooth' });
  }

  loadImages(): void {
    this.carrouselImageService.getImages().subscribe(data => {
      this.images = data;
    });
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  register(): void {
    this.router.navigate(['/account/register']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  selectZone(id: number): void {
    this.router.navigate(['/protocols/' + id]);
  }

  selectProtocol(protocol: any) {
    this.router.navigate(['/protocols', protocol.id]);
  }
}
