import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProtocolManagementService } from 'app/admin/protocol-management/service/protocol-management.service';

import { ProtocolDTO } from 'app/entities/protocol.model';

@Component({
  selector: 'jhi-protocol-mgmt-delete-dialog',
  templateUrl: './protocol-management-delete-dialog.component.html',
})
export class ProtocolManagementDeleteDialogComponent {
  protocol?: ProtocolDTO;

  constructor(private protocolManagementService: ProtocolManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.protocolManagementService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
