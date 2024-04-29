import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';

import { ProductManagementService } from './service/product-management.service';
import { ProductManagementComponent } from './list/product-management.component';
//import { ProductManagementDetailComponent } from './detail/product-management-detail.component';
import { ProductDTO } from 'app/entities/product.model';
import { ProductManagementUpdateComponent } from './update/product-management-update.component';
import { ProductManagementDetailComponent } from './detail/product-management-detail.component';

@Injectable({ providedIn: 'root' })
export class ProductManagementResolve implements Resolve<ProductDTO | null> {
  constructor(private service: ProductManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ProductDTO | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

export const ProductManagementRoute: Routes = [
  {
    path: '',
    component: ProductManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: ':id/view',
    component: ProductManagementDetailComponent,
    resolve: {
      product: ProductManagementResolve,
    },
  },
  {
    path: 'new',
    component: ProductManagementUpdateComponent,
    resolve: {
      product: ProductManagementResolve,
    },
  },
  {
    path: ':id/edit',
    component: ProductManagementUpdateComponent,
    resolve: {
      product: ProductManagementResolve,
    },
  },
];
