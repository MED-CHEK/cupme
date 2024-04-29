import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { PromoCodeManagementService } from '../service/promo-code-management.service';

@Component({
  selector: 'jhi-promo-code-mgmt-delete-dialog',
  templateUrl: './promo-code-management-delete-dialog.component.html',
})
export class PromoCodeManagementDeleteDialogComponent {
  promoCode?: number;

  constructor(private promoCodeManagementService: PromoCodeManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.promoCodeManagementService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
