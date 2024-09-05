import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { ASC, DESC, SORT } from 'app/config/navigation.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { ProductManagementService } from '../service/product-management.service';
import { ProductCartDTO } from 'app/entities/product.model';

@Component({
  selector: 'jhi-product-mgmt',
  templateUrl: './product-management.component.html',
  styleUrls: ['./product-management.component.scss'],
})
export class ProductManagementComponent implements OnInit {
  currentAccount: Account | null = null;
  products: ProductCartDTO[] | null = null;
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;

  constructor(
    private productService: ProductManagementService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.handleNavigation();
  }

  trackIdentity(_index: number, item: ProductCartDTO): number {
    return item.id!;
  }

  loadAll(): void {
    this.isLoading = true;
    this.productService.query().subscribe({
      next: (products: ProductCartDTO[]) => {
        this.products = products;
      },
      error: () => (this.isLoading = false),
      complete: () => (this.isLoading = false),
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

  getProductImage(product: ProductCartDTO): string {
    return product.picture.file ?? '../../../../content/images/Pictos/No-picture.svg';
  }

  view(product: ProductCartDTO) {
    this.router.navigate(['/admin/product-management/' + product.id, 'view']);
  }
}
