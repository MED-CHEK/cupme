import { OrderItemServerDTO } from './orderItem.model';

export interface OrderSeverDTO {
  transactionId: string;
  totalPrice: number;
  orderItemServerDTOs: OrderItemServerDTO[];
}
