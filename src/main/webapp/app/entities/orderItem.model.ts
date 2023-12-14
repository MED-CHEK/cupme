import { ProductType } from './product-type.enum';
import { ProtocolCartDTO } from './protocol.model';

export interface OrderItemServerDTO {
  productId: number;
  quantity: number;
  type: ProductType;
}
