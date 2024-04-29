import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { PromoCodeManagementComponent } from './list/promo-code-management.component';
import { PromoCodeManagementRoute } from './promo-code-management.route';
import { PromoCodeManagementDeleteDialogComponent } from './delete/promo-code-management-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(PromoCodeManagementRoute)],
  declarations: [PromoCodeManagementComponent, PromoCodeManagementDeleteDialogComponent],
})
export class PromoCodeManagementModule {}
