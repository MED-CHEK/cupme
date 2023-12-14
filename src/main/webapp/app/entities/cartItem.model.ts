import { CartDTO } from './cart.model';
import { PictureDTO } from './picture.model';
import { ProductType } from './product-type.enum';
import { ProductCartDTO } from './product.model';
import { ProtocolCartDTO } from './protocol.model';

export interface CartItemDTO {
  id?: number;
  cartDTO: CartDTO;
  protocolCartDTO?: ProtocolCartDTO;
  productCartDTO?: ProductCartDTO;
  quantity: number;
  createdDate?: string;
}

export interface CartItemDisplayDTO {
  productId: number;
  name: string;
  price: number;
  picture: string;
  createdDate: string;
  quantity: number;
  protocol: boolean;
}

export interface CartItemSession {
  productId: number;
  quantity: number;
  createdDate: string;
  type: ProductType;
}
