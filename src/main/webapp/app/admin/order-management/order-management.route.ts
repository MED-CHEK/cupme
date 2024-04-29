import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';

import { OrderManagementService } from './service/order-management.service';
import { OrderManagementComponent } from './list/order-management.component';
import { OrderItemDTO } from 'app/entities/orderItem.model';
import { OrderManagementUpdateComponent } from './update/order-management-update.component';

@Injectable({ providedIn: 'root' })
export class OrderManagementResolve implements Resolve<OrderItemDTO[] | null> {
  constructor(private service: OrderManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<OrderItemDTO[] | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.getByOrderId(id);
    }
    return of(null);
  }
}

export const OrderManagementRoute: Routes = [
  {
    path: '',
    component: OrderManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: 'new',
    component: OrderManagementUpdateComponent,
  },
  {
    path: ':id/edit',
    component: OrderManagementUpdateComponent,
    resolve: {
      order: OrderManagementResolve,
    },
  },
];
