import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { ProductManagementService } from '../service/product-management.service';
import { ProductDTOPics } from 'app/entities/product.model';
import { PictureDTO } from 'app/entities/picture.model';

const productTemplate = {} as ProductDTOPics;

const newProduct: ProductDTOPics = {
  type: '',
  name: '',
  price: 0,
  stock: 0,
  description: '',
  shortDescription: '',
  pictures: [] as PictureDTO[],
} as ProductDTOPics;

@Component({
  selector: 'jhi-product-mgmt-update',
  templateUrl: './product-management-update.component.html',
  styleUrls: ['./product-management-update.component.scss'],
})
export class ProductManagementUpdateComponent implements OnInit {
  isSaving = false;

  editForm = new FormGroup({
    id: new FormControl(productTemplate.id),
    name: new FormControl(productTemplate.name, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]),
    shortDescription: new FormControl(productTemplate.shortDescription),
    description: new FormControl(productTemplate.description),
    stock: new FormControl(productTemplate.stock),
    price: new FormControl(productTemplate.price, Validators.required),
  });

  uploadForm: FormGroup = new FormGroup({
    pictures: new FormControl(productTemplate.pictures, [Validators.required]),
  });

  constructor(private productService: ProductManagementService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ product }) => {
      if (product) {
        this.editForm.reset(product);
        this.uploadForm.patchValue({
          pictures: product.pictures,
        });
      } else {
        this.editForm.reset(newProduct);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pictures = this.uploadForm.get('pictures')?.value ?? [];
    if (pictures.length > 0) {
      pictures.forEach((picture: PictureDTO, index: number) => {
        picture.main = false;
      });
      pictures[0].main = true;
    }
    const product: ProductDTOPics = {
      id: this.editForm.get('id')?.value ?? null,
      name: this.editForm.get('name')?.value ?? '',
      shortDescription: this.editForm.get('shortDescription')?.value ?? '',
      description: this.editForm.get('description')?.value ?? '',
      price: this.editForm.get('price')?.value ?? 0,
      stock: this.editForm.get('stock')?.value ?? 0,
      type: '', // Ajoutez la propriété 'type' manquante
      pictures: pictures,
    } as ProductDTOPics;
    if (product.id !== null || product.id !== '') {
      this.productService.update(product).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.productService.create(product).subscribe({
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
