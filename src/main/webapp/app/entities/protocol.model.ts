import { CategoryDTO } from './category.model';
import { PictureDTO } from './picture.model';
import { ProductCartDTO, ProductDTO } from './product.model';

export interface ProtocolDTO {
  id: number;
  type: string;
  name: string;
  shortDescription: string;
  description: string;
  price: number;
  poseTime: number;
  pictures: PictureDTO[];
  productDTOs?: ProductDTO[];
}

export interface ProtocolCartDTO {
  id: number;
  name: string;
  price: number;
  picture: PictureDTO;
}

export interface ProtocolDetailDTO {
  id: number;
  type: string;
  name: string;
  shortDescription: string;
  description: string;
  price: number;
  poseTime: number;
  pictures: PictureDTO[];
  productDTOs?: ProductCartDTO[];
  categoryDTOs?: CategoryDTO[];
}

export interface MyProtocolDTO {
  id: number;
  name: string;
  poseTime: number;
  nbPoseTime: number;
  productDTO: ProductCartDTO;
  pictures: PictureDTO[];
}
