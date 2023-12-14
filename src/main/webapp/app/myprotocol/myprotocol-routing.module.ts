import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyResultComponent } from './myresult/myresult.component';
import { MyDetailComponent } from './mydetail/mydetail.component';
import { MyProtocolResolverService } from './myresult/myresult-resolver.service';

const routes: Routes = [
  {
    path: '',
    component: MyResultComponent,
    data: {
      pageTitle: 'global.myprotocol.title',
    },
  },
  {
    path: ':id',
    component: MyDetailComponent,
    resolve: {
      protocol: MyProtocolResolverService,
    },
    data: {
      pageTitle: 'global.detail.title',
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MyProtocolRoutingModule {}
