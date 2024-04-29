import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';

import { PromoCodeManagementService } from './service/promo-code-management.service';
import { PromoCodeManagementComponent } from './list/promo-code-management.component';
import { PromoCodeDTO } from 'app/entities/promo-code.model';

export const PromoCodeManagementRoute: Routes = [
  {
    path: '',
    component: PromoCodeManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
];
