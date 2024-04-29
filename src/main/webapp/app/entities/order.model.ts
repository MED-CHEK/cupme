import { OrderItemServerDTO } from './orderItem.model';
import { AppointmentInfo } from './session.model';
import { UserDTO } from './user/user.model';

export interface OrderSeverDTO {
  orderId?: number;
  userId?: number;
  transactionId: string;
  totalPrice: number;
  paid: boolean;
  orderItemServerDTOs: OrderItemServerDTO[];
}

export interface OrderDTO {
  id: number;
  transactionId: string;
  totalPrice: number;
  user: UserDTO;
  paid: boolean;
  createdDate: Date;
}
