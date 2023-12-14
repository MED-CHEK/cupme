import { CategoryDTO } from './category.model';
import { PictureDTO } from './picture.model';
import { ProtocolDTO } from './protocol.model';
import { TagDTO } from './tag.model';

export interface ProductDTO {
  id: number;
  type: string;
  name: string;
  shortDescription: string;
  description: string;
  price: number;
  stock?: number;
  width?: number;
  size?: number;
  height?: number;
  weight?: number;
  Protocols?: ProtocolDTO[];
  Categories?: CategoryDTO[];
  Tags?: TagDTO[];
  pictures: PictureDTO[];
}

export interface ProductCartDTO {
  id?: number;
  name: string;
  price: number;
  picture: PictureDTO;
}
