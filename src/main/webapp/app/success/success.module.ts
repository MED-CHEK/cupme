import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { SuccessComponent } from './success.component';
import { SUCCESS_ROUTE } from './success.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([SUCCESS_ROUTE])],
  declarations: [SuccessComponent],
})
export class SuccessModule {}
