import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'sec-upload-photo',
  templateUrl: './upload-photo.component.html',
  styleUrls: ['./upload-photo.component.scss'],
})
export class UploadPhotoComponent implements OnInit {
  @Input()
  uploadForm!: FormGroup;
  maxImageUpload: number = 6;
  images: { name: string; file: string }[] = [];
  pictures: any[] = [];

  constructor() {}

  ngOnInit(): void {
    if (this.uploadForm.value.pictures) {
      this.pictures = this.uploadForm.value.pictures;
    }

    this.pictures.map(picture => {
      this.images.push({ name: picture.name, file: 'data:image/jpg;base64,' + picture.file });
    });
  }

  openInput(fileInput: HTMLInputElement) {
    fileInput.click();
  }

  imageChange(event: any) {
    if (event.target.files && event.target.files.length) {
      for (let i = 0; i < event.target.files.length; i++) {
        let image = event.target.files[i];
        if (image.type != 'image/jpeg' && image.type != 'image/png') {
          this.createErrorImage();
        } else {
          this.readImage(image);
        }
      }
    }
  }

  async createErrorImage() {
    fetch('../../../assets/images/A80.jpg')
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
}
