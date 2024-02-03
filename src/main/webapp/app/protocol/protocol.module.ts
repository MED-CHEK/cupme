import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ResultComponent } from './result/result.component';
import { DetailComponent } from './detail/detail.component';
import { ProtocolRoutingModule } from './protocol-routing.module';

@NgModule({
  declarations: [ResultComponent, DetailComponent],
  imports: [SharedModule, ProtocolRoutingModule],
})
export class ProtocolModule {}
