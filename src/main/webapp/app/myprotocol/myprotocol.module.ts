import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { MyResultComponent } from './myresult/myresult.component';
import { MyDetailComponent } from './mydetail/mydetail.component';
import { MyProtocolRoutingModule } from './myprotocol-routing.module';
import { MyProtoComponent } from './myresult/myproto/myproto.component';
import { TimerComponent } from './mydetail/timer/timer.component';

@NgModule({
  declarations: [MyResultComponent, MyDetailComponent, MyProtoComponent, TimerComponent],
  imports: [SharedModule, MyProtocolRoutingModule],
})
export class MyProtocolModule {}
