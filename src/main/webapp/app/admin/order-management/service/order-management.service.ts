import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { OrderItemDTO } from 'app/entities/orderItem.model';
import { OrderDTO, OrderSeverDTO } from 'app/entities/order.model';
import { CartItemDisplayDTO } from 'app/entities/cartItem.model';
import { ProductType } from 'app/entities/product-type.enum';

@Injectable({ providedIn: 'root' })
export class OrderManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/orderItems');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  createOrder(cartItems: CartItemDisplayDTO[], orderSeverDTO: OrderSeverDTO): Observable<any> {
    let orderSeverDTOs = this.getOrderServerDTO(cartItems, orderSeverDTO);

    return this.http.post<any>(this.applicationConfigService.getEndpointFor('api/orderItems'), orderSeverDTOs);
  }

  updateOrder(cartItems: CartItemDisplayDTO[], orderSeverDTO: OrderSeverDTO): Observable<any> {
    let orderSeverDTOs = this.getOrderServerDTO(cartItems, orderSeverDTO);

    return this.http.put<any>(this.applicationConfigService.getEndpointFor('api/orderItems'), orderSeverDTOs);
  }

  private getOrderServerDTO(cartItems: CartItemDisplayDTO[], orderSeverDTO: OrderSeverDTO): OrderSeverDTO {
    cartItems.forEach(item => {
      let orderItem = {
        productId: item.productId,
        quantity: item.quantity,
        type: item.type ? ProductType.PROTOCOL : ProductType.PRODUCT,
      };
      orderSeverDTO.orderItemServerDTOs.push(orderItem);
    });
    return orderSeverDTO;
  }

  update(protocol: OrderDTO): Observable<OrderDTO> {
    return this.http.put<OrderDTO>(this.resourceUrl, protocol);
  }

  find(id: string): Observable<OrderItemDTO> {
    return this.http.get<OrderItemDTO>(`${this.resourceUrl}/${id}`);
  }

  query(): Observable<OrderItemDTO[]> {
    return this.http.get<OrderItemDTO[]>(this.resourceUrl);
  }

  getByOrderId(orderId: number): Observable<OrderItemDTO[]> {
    return this.http.get<OrderItemDTO[]>(this.applicationConfigService.getEndpointFor('api/orderItems/order') + `/${orderId}`);
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/authorities'));
  }
}
