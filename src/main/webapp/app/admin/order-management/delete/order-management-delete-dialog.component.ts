import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { OrderManagementService } from 'app/admin/order-management/service/order-management.service';

@Component({
  selector: 'jhi-order-mgmt-delete-dialog',
  templateUrl: './order-management-delete-dialog.component.html',
})
export class OrderManagementDeleteDialogComponent {
  order?: number;

  constructor(private orderManagementService: OrderManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderManagementService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
