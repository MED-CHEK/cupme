import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { STORE_ROUTE } from './store.route';
import { StoreComponent } from './store.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([STORE_ROUTE])],
  declarations: [StoreComponent],
})
export class StoreModule {}
