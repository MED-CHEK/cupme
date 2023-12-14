import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ResultComponent } from './result/result.component';
import { DetailComponent } from './detail/detail.component';
import { ProtocolRoutingModule } from './protocol-routing.module';
import { ProtoComponent } from './proto/proto.component';

@NgModule({
  declarations: [ResultComponent, DetailComponent, ProtoComponent],
  imports: [SharedModule, ProtocolRoutingModule],
})
export class ProtocolModule {}
