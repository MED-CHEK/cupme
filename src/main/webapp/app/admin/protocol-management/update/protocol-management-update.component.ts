import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { PictureDTO } from 'app/entities/picture.model';
import { ProtocolDTO, ProtocolDetailDTO } from 'app/entities/protocol.model';
import { ProtocolManagementService } from '../service/protocol-management.service';
import { ProductCartDTO } from 'app/entities/product.model';
import { ProductManagementService } from 'app/admin/product-management/service/product-management.service';

const protocolTemplate = {} as ProtocolDetailDTO;

const newProtocol: ProtocolDetailDTO = {
  type: '',
  name: '',
  price: 0,
  description: '',
  shortDescription: '',
  poseTime: 0,
  productDTOs: [] as ProductCartDTO[],
  pictures: [] as PictureDTO[],
} as ProtocolDetailDTO;

@Component({
  selector: 'jhi-protocol-mgmt-update',
  templateUrl: './protocol-management-update.component.html',
  styleUrls: ['./protocol-management-update.component.scss'],
})
export class ProtocolManagementUpdateComponent implements OnInit {
  isSaving = false;
  types = ['SIMPLE', 'GENERIC'];
  isLoading = false;
  products!: ProductCartDTO[];

  editForm = new FormGroup({
    id: new FormControl(protocolTemplate.id),
    type: new FormControl(protocolTemplate.type, Validators.required),
    name: new FormControl(protocolTemplate.name, [Validators.required, Validators.minLength(1)]),
    shortDescription: new FormControl(protocolTemplate.shortDescription),
    description: new FormControl(protocolTemplate.description),
    price: new FormControl(protocolTemplate.price, Validators.required),
    poseTime: new FormControl(protocolTemplate.poseTime, Validators.required),
    productDTOs: new FormControl(protocolTemplate.productDTOs, Validators.required),
  });

  uploadForm: FormGroup = new FormGroup({
    pictures: new FormControl(protocolTemplate.pictures, [Validators.required]),
  });

  constructor(
    private protocolService: ProtocolManagementService,
    private productService: ProductManagementService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ protocol }) => {
      if (protocol) {
        this.editForm.reset(protocol);
        this.uploadForm.patchValue({
          pictures: protocol.pictures,
        });
      } else {
        this.editForm.reset(newProtocol);
      }
    });

    this.isLoading = true;
    this.productService.query().subscribe({
      next: (products: ProductCartDTO[]) => {
        this.products = products.filter(
          product => this.editForm.value.productDTOs?.find((productDTO: ProductCartDTO) => productDTO.id === product.id) === undefined
        );
      },
      error: () => (this.isLoading = false),
      complete: () => (this.isLoading = false),
    });
  }

  getProductImage(product: ProductCartDTO): string {
    return product.picture.file ?? '../../../../content/images/Pictos/No-picture.svg';
  }

  addProduct(product: ProductCartDTO): void {
    const products = this.editForm.get('productDTOs')?.value ?? [];
    products.push(product);
    this.products.splice(this.products.indexOf(product), 1);
    this.editForm.patchValue({
      productDTOs: products,
    });
  }

  removeProduct(product: ProductCartDTO): void {
    const products = this.editForm.get('productDTOs')?.value ?? [];
    const index = products.indexOf(product);
    if (index > -1) {
      products.splice(index, 1);
      this.products.push(product);
    }
    this.editForm.patchValue({
      productDTOs: products,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pictures = this.uploadForm.get('pictures')?.value ?? {};
    if (pictures.length > 0) {
      pictures.forEach((picture: PictureDTO, index: number) => {
        picture.main = false;
      });
      pictures[0].main = true;
    }
    const protocol: ProtocolDTO = {
      id: this.editForm.get('id')?.value ?? undefined,
      name: this.editForm.get('name')?.value ?? '',
      shortDescription: this.editForm.get('shortDescription')?.value ?? '',
      description: this.editForm.get('description')?.value ?? '',
      price: this.editForm.get('price')?.value ?? 0,
      type: this.editForm.get('type')?.value ?? '',
      poseTime: this.editForm.get('poseTime')?.value,
      productDTOs: this.editForm.get('productDTOs')?.value,
      pictures: pictures,
    } as ProtocolDTO;

    if (protocol.id !== null && typeof protocol.id === 'number') {
      this.protocolService.update(protocol).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.protocolService.create(protocol).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
