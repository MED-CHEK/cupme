import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { PictureDTO } from 'app/entities/picture.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ProtocolDTO, ProtocolDetailDTO } from 'app/entities/protocol.model';
import { ProtocolManagementDeleteDialogComponent } from '../delete/protocol-management-delete-dialog.component';
import { ProductCartDTO } from 'app/entities/product.model';

@Component({
  selector: 'jhi-protocol-mgmt-detail',
  templateUrl: './protocol-management-detail.component.html',
  styleUrls: ['./protocol-management-detail.component.scss'],
})
export class ProtocolManagementDetailComponent implements OnInit {
  protocol!: ProtocolDetailDTO;
  isLoading = false;

  constructor(private route: ActivatedRoute, private sanitizer: DomSanitizer, private modalService: NgbModal, private router: Router) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ protocol }) => {
      this.protocol = protocol;
    });
  }

  getProtocolImage(picture: PictureDTO): string {
    let path = picture.file;
    let imagePath!: string;
    if (path == undefined) {
      imagePath = '../../../../content/images/Pictos/No-picture.svg';
    } else {
      imagePath = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + path) as string;
    }
    return imagePath;
  }

  getProductImage(product: ProductCartDTO): string {
    let path = product.picture.file;
    let imagePath!: string;
    if (path == undefined) {
      imagePath = '../../../../content/images/Pictos/No-picture.svg';
    } else {
      imagePath = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + path) as string;
    }
    return imagePath;
  }

  deleteProtocol(protocol: ProtocolDetailDTO): void {
    const modalRef = this.modalService.open(ProtocolManagementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.protocol = protocol;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.router.navigate(['/admin/protocol-management/']);
      }
    });
  }
}
