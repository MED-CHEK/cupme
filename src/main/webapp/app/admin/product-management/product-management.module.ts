import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { ProductManagementComponent } from './list/product-management.component';
import { ProductManagementRoute } from './product-management.route';
import { ProductManagementUpdateComponent } from './update/product-management-update.component';
import { ProductManagementDetailComponent } from './detail/product-management-detail.component';
import { ProductManagementDeleteDialogComponent } from './delete/product-management-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(ProductManagementRoute)],
  declarations: [
    ProductManagementComponent,
    ProductManagementUpdateComponent,
    ProductManagementDetailComponent,
    ProductManagementDeleteDialogComponent,
  ],
})
export class ProductManagementModule {}
