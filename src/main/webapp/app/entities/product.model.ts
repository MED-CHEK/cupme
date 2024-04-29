import { PictureDTO } from './picture.model';

export interface ProductDTO {
  id: number;
  type: string;
  name: string;
  shortDescription: string;
  description: string;
  price: number;
  stock: number;
  lastModifiedDate: number;
  pictures: PictureDTO[];
}

export interface ProductDTOPics {
  id: number;
  type: string;
  name: string;
  shortDescription: string;
  description: string;
  price: number;
  stock: number;
  pictures: PictureDTO[];
}

export interface ProductCartDTO {
  id?: number;
  name: string;
  price: number;
  stock: number;
  picture: PictureDTO;
}
