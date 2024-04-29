import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from '../core/config/application-config.service';
import { CartItemDisplayDTO } from '../entities/cartItem.model';
import { LocalStorageService } from 'ngx-webstorage';
import { Observable } from 'rxjs';
import { OrderSeverDTO } from '../entities/order.model';
import { ProductType } from '../entities/product-type.enum';
import { ProtocolCartDTO } from '../entities/protocol.model';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getOrders(): Observable<any> {
    return this.http.get<any>(this.applicationConfigService.getEndpointFor('api/orders'));
  }

  createOrder(cartItems: CartItemDisplayDTO[], transactionId: string): Observable<any> {
    let orderSeverDTO = this.getOrderServerDTO(cartItems, transactionId);

    return this.http.post<any>(this.applicationConfigService.getEndpointFor('api/orderItems'), orderSeverDTO);
  }

  private getOrderServerDTO(cartItems: CartItemDisplayDTO[], transactionId: string): OrderSeverDTO {
    let orderSeverDTO: OrderSeverDTO = {
      transactionId: transactionId,
      totalPrice: cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0),
      paid: true,
      orderItemServerDTOs: [],
    };
    cartItems.forEach(item => {
      if (item.type === ProductType.PROTOCOL || item.type === ProductType.PRODUCT) {
        orderSeverDTO.orderItemServerDTOs.push({
          productId: item.productId,
          quantity: item.quantity,
          type: item.type,
        });
      } else {
        orderSeverDTO.orderItemServerDTOs.push({
          productId: item.productId,
          quantity: item.quantity,
          type: item.type,
          appointmentInfo: item.appointmentInfo,
        });
      }
    });
    return orderSeverDTO;
  }

  getOrderProtocols(): Observable<ProtocolCartDTO[]> {
    return this.http.get<ProtocolCartDTO[]>(this.applicationConfigService.getEndpointFor('api/orderProtocols'));
  }
}
