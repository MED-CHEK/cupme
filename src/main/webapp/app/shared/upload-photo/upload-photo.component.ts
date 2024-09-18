import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'sec-upload-photo',
  templateUrl: './upload-photo.component.html',
  styleUrls: ['./upload-photo.component.scss'],
})
export class UploadPhotoComponent implements OnInit {
  @Input()
  uploadForm!: FormGroup;
  maxImageUpload: number = 6;
  images: { name: string; file: string; path?: string }[] = [];
  pictures: any[] = [];

  constructor() {}

  async ngOnInit() {
    await this.updatePicturesWithBase64(); // Appel de la fonction dans ngOnInit

    this.pictures.map(picture => {
      this.images.push({ name: picture.name, file: picture.file });
    });
  }

  openInput(fileInput: HTMLInputElement) {
    fileInput.click();
  }

  imageChange(event: any) {
    if (event.target.files && event.target.files.length) {
      for (let i = 0; i < event.target.files.length; i++) {
        let image = event.target.files[i];
        if (image.type != 'image/jpeg' && image.type != 'image/png' && image.type != 'image/jpg') {
          this.createErrorImage();
        } else {
          this.readImage(image);
        }
      }
    }
  }

  async createErrorImage() {
    fetch('../../../content/images/No-picture.svg')
      .then(res => res.blob())
      .then(blob => {
        this.readImage(blob);
      });
  }

  readImage(image: any) {
    var reader = new FileReader();
    reader.onload = (e: any) => {
      this.images = this.images.filter(img => img != e.target.result);
      this.images.push({ name: image.name, file: e.target.result });
      this.images.splice(this.maxImageUpload, this.images.length);
      this.patchImageValues();
    };
    reader.readAsDataURL(image);
  }

  patchImageValues() {
    this.uploadForm.patchValue({
      pictures: this.images,
    });
  }

  // Remove Image
  removeImage(url: any) {
    this.images = this.images.filter(img => img != url);
    this.patchImageValues();
  }

  async getBase64ImageFromUrl(imageUrl: any) {
    var res = await fetch(imageUrl);
    var blob = await res.blob();

    return new Promise((resolve, reject) => {
      var reader = new FileReader();
      reader.addEventListener(
        'load',
        function () {
          resolve(reader.result);
        },
        false
      );

      reader.onerror = () => {
        return reject(this);
      };
      reader.readAsDataURL(blob);
    });
  }

  async updatePicturesWithBase64() {
    if (this.uploadForm.value.pictures) {
      this.pictures = this.uploadForm.value.pictures;

      // Convertir toutes les images en base64
      for (let i = 0; i < this.pictures.length; i++) {
        const picture = this.pictures[i];
        if (picture.file) {
          try {
            const base64Image = await this.getBase64ImageFromUrl(picture.file);
            this.pictures[i].file = base64Image; // Mise à jour du champ `file` avec la chaîne base64
          } catch (error) {
            console.error("Erreur lors de la conversion de l'image:", error);
          }
        }
      }
    }
  }
}
