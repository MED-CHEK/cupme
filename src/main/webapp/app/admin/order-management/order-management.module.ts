import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { OrderManagementComponent } from './list/order-management.component';
import { OrderManagementRoute } from './order-management.route';
import { OrderManagementUpdateComponent } from './update/order-management-update.component';
import { OrderManagementDeleteDialogComponent } from './delete/order-management-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(OrderManagementRoute)],
  declarations: [OrderManagementComponent, OrderManagementUpdateComponent, OrderManagementDeleteDialogComponent],
})
export class OrderManagementModule {}
