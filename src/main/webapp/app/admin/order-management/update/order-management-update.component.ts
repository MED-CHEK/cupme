import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { OrderManagementService } from '../service/order-management.service';
import { ProductCartDTO } from 'app/entities/product.model';
import { ProductManagementService } from 'app/admin/product-management/service/product-management.service';
import { OrderItemByOrderDTO, OrderItemByOrderIdDTO, OrderItemDTO, OrderProductDTO } from 'app/entities/orderItem.model';
import { ProtocolCartDTO } from 'app/entities/protocol.model';
import { ProtocolManagementService } from 'app/admin/protocol-management/service/protocol-management.service';
import { ClientUserDTO } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { CartItemDisplayDTO } from 'app/entities/cartItem.model';
import { OrderSeverDTO } from 'app/entities/order.model';
import { ProductType } from 'app/entities/product-type.enum';

const orderTemplate = {} as OrderItemByOrderDTO;

const newOrder: OrderItemByOrderDTO = {
  orderUserId: '',
  orderTotalPrice: 0,
  ordertransactionId: '',
  orderPaid: false,
  orderDate: new Date(),
  protocols: [] as ProtocolCartDTO[],
  orderProducts: [] as OrderProductDTO[],
} as OrderItemByOrderDTO;

@Component({
  selector: 'jhi-order-mgmt-update',
  templateUrl: './order-management-update.component.html',
  styleUrls: ['./order-management-update.component.scss'],
})
export class OrderManagementUpdateComponent implements OnInit {
  isSaving = false;
  isLoading = false;
  products!: ProductCartDTO[];
  protocols!: ProtocolCartDTO[];
  users!: ClientUserDTO[];
  ordreItems: CartItemDisplayDTO[] = [];
  orderItemsbyOrderId!: OrderItemByOrderIdDTO;
  type = ProductType;
  editForm = new FormGroup({
    orderId: new FormControl(orderTemplate.orderId),
    orderUserId: new FormControl(orderTemplate.orderUserId, Validators.required),
    orderTotalPrice: new FormControl(orderTemplate.orderTotalPrice, Validators.required),
    newPrice: new FormControl(''),
    orderPaid: new FormControl(orderTemplate.orderPaid, Validators.required),
    transactionId: new FormControl(''),
    protocols: new FormControl(orderTemplate.protocols),
    orderProducts: new FormControl(orderTemplate.orderProducts),
  });

  constructor(
    private orderService: OrderManagementService,
    private protocolService: ProtocolManagementService,
    private productService: ProductManagementService,
    private userService: UserService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ order }) => {
      if (order) {
        this.loadOrderInfo(order);
        this.editForm.patchValue({
          orderId: this.orderItemsbyOrderId.orderId,
          orderUserId: this.orderItemsbyOrderId.orderUserName,
          orderTotalPrice: this.orderItemsbyOrderId.orderTotalPrice,
          orderPaid: this.orderItemsbyOrderId.orderPaid,
          protocols: this.orderItemsbyOrderId.protocols,
          orderProducts: this.orderItemsbyOrderId.orderProducts,
        });
      } else {
        this.editForm.reset(newOrder);
      }
    });

    this.isLoading = true;

    this.userService.getClients().subscribe({
      next: (users: ClientUserDTO[]) => {
        this.users = users;
      },
      error: () => (this.isLoading = false),
      complete: () => (this.isLoading = false),
    });

    this.protocolService.query().subscribe({
      next: (protocols: ProtocolCartDTO[]) => {
        this.protocols = protocols.filter(
          protocol =>
            this.editForm.value.protocols?.find((protocolCartDTO: ProtocolCartDTO) => protocolCartDTO.id === protocol.id) === undefined
        );
      },
      error: () => (this.isLoading = false),
      complete: () => (this.isLoading = false),
    });

    this.productService.query().subscribe({
      next: (products: ProductCartDTO[]) => {
        this.products = products.filter(
          product =>
            this.editForm.value.orderProducts?.find((orderProductDTO: OrderProductDTO) => orderProductDTO.product.id === product.id) ===
            undefined
        );
      },
      error: () => (this.isLoading = false),
      complete: () => (this.isLoading = false),
    });
  }

  loadOrderInfo(order: OrderItemDTO[]): void {
    this.isLoading = true;
    this.orderItemsbyOrderId = order.reduce((acc: OrderItemByOrderIdDTO[], orderItem: OrderItemDTO) => {
      const existingOrder = acc.find(o => o.orderId === orderItem.order.id);
      if (existingOrder) {
        existingOrder.orderId = orderItem.order.id;
        existingOrder.orderUserName = orderItem.order.user.firstName + ' ' + orderItem.order.user.lastName;
        existingOrder.orderTotalPrice = orderItem.order.totalPrice;
        existingOrder.orderPaid = orderItem.order.paid;
        existingOrder.ordertransactionId = orderItem.order.transactionId;
        if (orderItem.protocol) {
          existingOrder.protocols.push(orderItem.protocol);
        }
        if (orderItem.product) {
          existingOrder.orderProducts.push({ product: orderItem.product, quantity: orderItem.quantity });
        }
      } else {
        acc.push({
          orderId: orderItem.order.id,
          orderUserName: orderItem.order.user.firstName + ' ' + orderItem.order.user.lastName,
          orderTotalPrice: orderItem.order.totalPrice,
          orderPaid: orderItem.order.paid,
          orderDate: orderItem.order.createdDate,
          ordertransactionId: orderItem.order.transactionId,
          protocols: orderItem.protocol ? [orderItem.protocol] : [],
          orderProducts: orderItem.product ? [{ product: orderItem.product, quantity: orderItem.quantity }] : [],
        });
      }
      return acc;
    }, [])[0];
  }

  increaseQuantity(orderProductDTO: OrderProductDTO) {
    orderProductDTO.quantity ? orderProductDTO.quantity++ : (orderProductDTO.quantity = 1);
  }

  reduceQuantity(orderProductDTO: OrderProductDTO) {
    orderProductDTO.quantity--;
    if (orderProductDTO.quantity <= 0) {
      const orderProducts = this.editForm.get('orderProducts')?.value;
      if (orderProducts) {
        this.editForm.patchValue({
          orderProducts: orderProducts.filter((orderProduct: OrderProductDTO) => orderProduct.product.id !== orderProductDTO.product.id),
        });
      }
      this.products.push(orderProductDTO.product);
    }
  }

  addProtocol(protocol: ProtocolCartDTO): void {
    const protocols: ProtocolCartDTO[] = this.editForm.get('protocols')?.value ?? [];

    protocols.push(protocol);

    this.protocols.splice(this.protocols.indexOf(protocol), 1);
    this.editForm.patchValue({
      protocols: protocols,
    });
  }

  removeProtocol(protocol: ProtocolCartDTO): void {
    const protocols: ProtocolCartDTO[] = this.editForm.get('protocols')?.value ?? [];
    const index = protocols.indexOf(protocol);
    if (index > -1) {
      protocols.splice(index, 1);
      this.protocols.push(protocol);
    }
    this.editForm.patchValue({
      protocols: protocols,
    });
  }

  addProduct(product: ProductCartDTO): void {
    const products: OrderProductDTO[] = this.editForm.get('orderProducts')?.value ?? [];

    products.push({ product: product, quantity: 1 });

    this.products.splice(this.products.indexOf(product), 1);
    this.editForm.patchValue({
      orderProducts: products,
    });
  }

  removeProduct(product: ProductCartDTO): void {
    const products: OrderProductDTO[] = this.editForm.get('orderProducts')?.value ?? [];
    const index = products.findIndex((orderProduct: OrderProductDTO) => orderProduct.product.id === product.id);
    if (index > -1) {
      products.splice(index, 1);
      this.products.push(product);
    }
    this.editForm.patchValue({
      orderProducts: products,
    });
  }

  previousState(): void {
    window.history.back();
  }

  getTotalPrice(): number {
    let totalPrice: number = 0;
    this.editForm.value.protocols?.forEach((protocol: ProtocolCartDTO) => {
      totalPrice += protocol.price;
    });
    this.editForm.value.orderProducts?.forEach((product: OrderProductDTO) => {
      totalPrice += product.product.price * product.quantity;
    });
    return totalPrice;
  }

  save(): void {
    this.isSaving = true;
    const order = this.editForm.value;
    const orderInfo: OrderSeverDTO = {
      orderId: order.orderId ? Number(order.orderId) : undefined,
      userId: order.orderUserId ? Number(order.orderUserId) : 0,
      transactionId: this.orderItemsbyOrderId?.ordertransactionId ?? 'PAS_DE_TRANSACTION',
      totalPrice: order.newPrice ? Number(order.newPrice) : this.getTotalPrice(),
      paid: order.orderPaid ?? false,
      orderItemServerDTOs: [],
    };
    this.isSaving = false;

    order.protocols?.forEach((protocol: ProtocolCartDTO) => {
      const orderItem: CartItemDisplayDTO = {
        productId: protocol.id ? protocol.id : 0,
        name: protocol.name,
        price: protocol.price,
        picture: (('../../content/images/' + protocol.id + '/' + protocol.picture.name) as string) + '.png',
        type: this.type.PROTOCOL,
        createdDate: new Date().toISOString(),
        quantity: 1,
      };
      this.ordreItems.push(orderItem);
    });

    order.orderProducts?.forEach((product: OrderProductDTO) => {
      const orderItem: CartItemDisplayDTO = {
        productId: product.product.id ? product.product.id : 0,
        name: product.product.name,
        price: product.product.price,
        picture: (('../../content/images/' + product.product.id + '/' + product.product.picture.name) as string) + '.png',
        type: this.type.PRODUCT,
        createdDate: new Date().toISOString(),
        quantity: product.quantity,
      };
      this.ordreItems.push(orderItem);
    });

    if (order.orderId !== null && typeof order.orderId === 'number') {
      this.orderService.updateOrder(this.ordreItems, orderInfo).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.orderService.createOrder(this.ordreItems, orderInfo).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
