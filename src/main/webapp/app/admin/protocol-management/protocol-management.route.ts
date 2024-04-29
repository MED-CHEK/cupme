import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { Observable, map, of } from 'rxjs';

import { ProtocolDetailDTO } from 'app/entities/protocol.model';
import { protocolManagementComponent } from './list/protocol-management.component';
import { ProtocolManagementService } from './service/protocol-management.service';
import { ProtocolManagementUpdateComponent } from './update/protocol-management-update.component';
import { ProtocolManagementDetailComponent } from './detail/protocol-management-detail.component';
import { ProductCartDTO } from 'app/entities/product.model';
import { ProductManagementService } from '../product-management/service/product-management.service';

@Injectable({ providedIn: 'root' })
export class ProtocolManagementResolve implements Resolve<ProtocolDetailDTO | null> {
  constructor(private service: ProtocolManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ProtocolDetailDTO | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

@Injectable({ providedIn: 'root' })
export class ProductManagementResolve implements Resolve<ProductCartDTO | null> {
  constructor(private service: ProductManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ProductCartDTO | null> {
    return this.service.query().pipe(map(products => (products.length > 0 ? products[0] : null)));
  }
}

export const ProtocolManagementRoute: Routes = [
  {
    path: '',
    component: protocolManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: ':id/view',
    component: ProtocolManagementDetailComponent,
    resolve: {
      protocol: ProtocolManagementResolve,
    },
  },
  {
    path: 'new',
    component: ProtocolManagementUpdateComponent,
    resolve: {
      protocol: ProtocolManagementResolve,
    },
  },
  {
    path: ':id/edit',
    component: ProtocolManagementUpdateComponent,
    resolve: {
      protocol: ProtocolManagementResolve,
    },
  },
];
