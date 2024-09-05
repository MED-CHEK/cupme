import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PictureDTO } from 'app/entities/picture.model';
import { ProductDTO } from 'app/entities/product.model';
import { ProductManagementDeleteDialogComponent } from '../delete/product-management-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-user-mgmt-detail',
  templateUrl: './product-management-detail.component.html',
  styleUrls: ['./product-management-detail.component.scss'],
})
export class ProductManagementDetailComponent implements OnInit {
  product!: ProductDTO;

  constructor(private route: ActivatedRoute, private modalService: NgbModal, private router: Router) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ product }) => {
      this.product = product;
    });
  }

  getProductImage(picture: PictureDTO): string {
    return picture.file ?? '../../../../content/images/Pictos/No-picture.svg';
  }

  deleteProduct(product: ProductDTO): void {
    const modalRef = this.modalService.open(ProductManagementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.product = product;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.router.navigate(['/admin/product-management/']);
      }
    });
  }
}
