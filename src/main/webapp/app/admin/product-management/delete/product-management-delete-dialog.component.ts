import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ProductManagementService } from '../service/product-management.service';
import { ProductDTO } from 'app/entities/product.model';

@Component({
  selector: 'jhi-product-mgmt-delete-dialog',
  templateUrl: './product-management-delete-dialog.component.html',
})
export class ProductManagementDeleteDialogComponent {
  product?: ProductDTO;

  constructor(private productManagementService: ProductManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productManagementService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
