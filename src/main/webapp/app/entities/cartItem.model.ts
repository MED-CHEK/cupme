import { CartDTO } from './cart.model';
import { ProductType } from './product-type.enum';
import { ProductCartDTO } from './product.model';
import { ProtocolCartDTO } from './protocol.model';
import { AppointmentInfo } from './session.model';

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
  type: ProductType;
  appointmentInfo?: AppointmentInfo;
}

export interface CartItemSession {
  productId: number;
  quantity: number;
  createdDate: string;
  type: ProductType;
}
