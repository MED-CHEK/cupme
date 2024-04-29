import { OrderDTO } from './order.model';
import { ProductType } from './product-type.enum';
import { ProductCartDTO } from './product.model';
import { ProtocolCartDTO } from './protocol.model';
import { AppointmentDTO, AppointmentInfo } from './session.model';

export interface OrderItemServerDTO {
  productId: number;
  quantity: number;
  type: ProductType;
  appointmentInfo?: AppointmentInfo;
}

export interface OrderItemDTO {
  id: number;
  quantity: number;
  order: OrderDTO;
  protocol: ProtocolCartDTO;
  product: ProductCartDTO;
}

export interface OrderProductDTO {
  quantity: number;
  product: ProductCartDTO;
}

export interface OrderItemByOrderIdDTO {
  orderId: number;
  orderUserName: string;
  orderTotalPrice: number;
  ordertransactionId: string;
  orderPaid: boolean;
  orderDate: Date;
  protocols: ProtocolCartDTO[];
  orderProducts: OrderProductDTO[];
  appointmentDTO?: AppointmentDTO[];
}

export interface OrderItemByOrderDTO {
  orderId: number;
  orderUserId: string;
  orderTotalPrice: number;
  ordertransactionId: string;
  orderPaid: boolean;
  orderDate: Date;
  protocols: ProtocolCartDTO[];
  orderProducts: OrderProductDTO[];
}
