import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { ProtocolManagementRoute } from './protocol-management.route';
import { protocolManagementComponent } from './list/protocol-management.component';
import { ProtocolManagementUpdateComponent } from './update/protocol-management-update.component';
import { ProtocolManagementDetailComponent } from './detail/protocol-management-detail.component';
import { ProtocolManagementDeleteDialogComponent } from './delete/protocol-management-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(ProtocolManagementRoute)],
  declarations: [
    protocolManagementComponent,
    ProtocolManagementUpdateComponent,
    ProtocolManagementDetailComponent,
    ProtocolManagementDeleteDialogComponent,
  ],
})
export class ProtocolManagementModule {}
