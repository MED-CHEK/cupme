import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { combineLatest } from 'rxjs';
import { ASC, DESC, SORT } from 'app/config/navigation.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { PromoCodeManagementService } from '../service/promo-code-management.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PromoCodeManagementDeleteDialogComponent } from '../delete/promo-code-management-delete-dialog.component';
import { ActivatedRoute, Router } from '@angular/router';
import { PromoCodeDTO } from 'app/entities/promo-code.model';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

const promoCodeTemplate = {} as PromoCodeDTO;

const newPromoCode: PromoCodeDTO = {
  code: '',
  discount: 0,
  activated: false,
  createdDate: undefined,
} as PromoCodeDTO;

@Component({
  selector: 'jhi-promo-code-mgmt',
  templateUrl: './promo-code-management.component.html',
  styleUrls: ['./promo-code-management.component.scss'],
})
export class PromoCodeManagementComponent implements OnInit {
  currentAccount: Account | null = null;
  promoCodes: PromoCodeDTO[] | null = null;
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  screenWidth!: number;
  isMobileDisplay!: boolean;

  savePromoCode = false;

  isSaving = false;
  promoCodeError = false;
  editForm = new FormGroup({
    id: new FormControl(promoCodeTemplate.id),
    code: new FormControl(promoCodeTemplate.code, Validators.required),
    discount: new FormControl(promoCodeTemplate.discount, [Validators.required, Validators.min(0), Validators.max(100)]),
    activated: new FormControl(promoCodeTemplate.activated),
  });

  constructor(
    private promoCodeService: PromoCodeManagementService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private modalService: NgbModal,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.onWindowResize();
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.handleNavigation();
  }

  convertToUpperCase() {
    const codeControl = this.editForm.get('code');
    if (codeControl) {
      codeControl.valueChanges.subscribe(value => {
        if (value) {
          codeControl.setValue(value.toUpperCase(), { emitEvent: false });
        }
      });
    }
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

  setActive(promoCode: PromoCodeDTO, isActivated: boolean): void {
    this.promoCodeService.update({ ...promoCode, activated: isActivated }).subscribe(() => this.loadAll());
  }

  trackIdentity(_index: number, item: PromoCodeDTO): number {
    return item.id!;
  }

  loadAll(): void {
    this.isLoading = true;
    this.promoCodeService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<PromoCodeDTO[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        error: () => (this.isLoading = false),
      });
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: `${this.predicate},${this.ascending ? ASC : DESC}`,
      },
    });
  }

  private handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      this.page = +(page ?? 1);
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      this.predicate = sort[0];
      this.ascending = sort[1] === ASC;
      this.loadAll();
    });
  }

  private sort(): string[] {
    const result = [`${this.predicate},${this.ascending ? ASC : DESC}`];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  save(): void {
    this.isSaving = true;
    this.promoCodeError = false;
    const codePromoInfo: PromoCodeDTO = {
      id: this.editForm.get('id')?.value ?? undefined,
      code: this.editForm.get('code')?.value ?? '',
      discount: this.editForm.get('discount')?.value ?? 0,
      activated: this.editForm.get('activated')?.value ?? false,
    };

    if (codePromoInfo.id !== undefined) {
      this.promoCodeService.update(codePromoInfo).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.promoCodeService.create(codePromoInfo).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  private onSaveSuccess(): void {
    this.loadAll();
    this.isSaving = false;
    this.savePromoCode = false;
  }

  private onSaveError(): void {
    this.isSaving = false;
    this.promoCodeError = true;
  }

  private onSuccess(promoCodes: PromoCodeDTO[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.promoCodes = promoCodes;
  }

  updatePromoCode(promoCode: PromoCodeDTO): void {
    this.savePromoCode = true;
    this.editForm.patchValue({
      id: promoCode.id,
      code: promoCode.code,
      discount: promoCode.discount,
      activated: promoCode.activated,
    });
  }

  deletePromoCode(promoCodeId: number): void {
    const modalRef = this.modalService.open(PromoCodeManagementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.promoCode = promoCodeId;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
