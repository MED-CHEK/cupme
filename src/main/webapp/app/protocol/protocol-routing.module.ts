import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ResultComponent } from './result/result.component';
import { DetailComponent } from './detail/detail.component';
import { ProtocolResolverService } from './result/result-resolver.service';

const routes: Routes = [
  {
    path: '',
    component: ResultComponent,
    resolve: {},
    data: {
      pageTitle: 'global.protocol.title',
    },
  },
  {
    path: ':id',
    component: DetailComponent,
    data: {
      pageTitle: 'global.detail.title',
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProtocolRoutingModule {}
