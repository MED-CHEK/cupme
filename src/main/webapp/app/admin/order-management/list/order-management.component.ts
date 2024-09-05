import { Component, HostListener, OnInit } from '@angular/core';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { OrderItemByOrderIdDTO, OrderItemDTO } from 'app/entities/orderItem.model';
import { OrderManagementService } from '../service/order-management.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { OrderManagementDeleteDialogComponent } from '../delete/order-management-delete-dialog.component';
import { Router } from '@angular/router';
import { AppointmentService } from 'app/services/appointment.service';
import { AppointmentDTO, ContactType } from 'app/entities/session.model';

@Component({
  selector: 'jhi-order-mgmt',
  templateUrl: './order-management.component.html',
  styleUrls: ['./order-management.component.scss'],
})
export class OrderManagementComponent implements OnInit {
  currentAccount: Account | null = null;
  orderItemsbyOrderId: OrderItemByOrderIdDTO[] | null = null;
  isLoading = false;
  fullList: OrderItemByOrderIdDTO[] = [];
  paginatedList: OrderItemByOrderIdDTO[] = [];
  itemsPerPage: number = 5;
  page: number = 1;
  screenWidth!: number;
  isMobileDisplay!: boolean;
  orderItems: OrderItemDTO[] = [];
  appointments: AppointmentDTO[] = [];
  filteredAppointments: AppointmentDTO[] = [];

  staticAppointments: AppointmentDTO[] = [
    {
      id: 1,
      email: '',
      telephone: '0708058161',
      type: ContactType.WHATSAPP,
      appointmentDate: '2024-12-06',
      appointmentTime: '15:22:00',
      session: {
        id: 1,
        name: 'Séance 15 min',
        duration: 15.0,
        price: 24.99,
      },
      order: {
        id: 59,
        user: {
          id: 11,
          login: 'achraf',
          password: 'qweqrwe',
          firstName: 'Achraf',
          lastName: 'Jallaglag',
          email: 'achraf@gmail.com',
          sex: 'HOMME',
          age: 26,
          weight: 60,
          size: 178,
        },
        paid: true,
        totalPrice: 65.97,
        transactionId: 'PAS_DE_TRANSACTION',
        createdDate: new Date(),
      },
    },
    {
      id: 2,
      email: '',
      telephone: '0602555555',
      type: ContactType.WHATSAPP,
      appointmentDate: '2024-04-29',
      appointmentTime: '16:00:00',
      session: {
        id: 1,
        name: 'Séance 15 min',
        duration: 15.0,
        price: 24.99,
      },
      order: {
        id: 75,
        user: {
          id: 1,
          login: 'admin',
          password: 'jhudshk',
          firstName: 'Administrator',
          lastName: 'Administrator',
          email: 'chekrouni50@gmail.com',
          sex: 'HOMME',
          age: 38,
          weight: 75,
          size: 175,
        },
        paid: true,
        totalPrice: 0.0,
        transactionId: '0GS24599XB9243931',
        createdDate: new Date(),
      },
    },
  ];
  constructor(
    private orderService: OrderManagementService,
    private accountService: AccountService,
    private appointmentService: AppointmentService,
    private modalService: NgbModal,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.onWindowResize();
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.loadAll();
  }

  @HostListener('window:resize', ['$event'])
  onWindowResize() {
    this.screenWidth = window.innerWidth;
    if (this.screenWidth < 768) {
      this.isMobileDisplay = true;
    } else {
      this.isMobileDisplay = false;
    }
  }
  trackIdentity(_index: number, item: OrderItemDTO): number {
    return item.id!;
  }

  loadAll(): void {
    this.isLoading = true;
    this.orderService.query().subscribe({
      next: (orderItems: OrderItemDTO[]) => {
        this.orderItemsbyOrderId = orderItems.reduce((acc: OrderItemByOrderIdDTO[], orderItem: OrderItemDTO) => {
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
        }, []);

        this.fullList = this.orderItemsbyOrderId?.sort((a, b) => new Date(b.orderDate).getTime() - new Date(a.orderDate).getTime()) ?? [];
        this.updatePagination();
      },
      error: () => (this.isLoading = false),
      complete: () => (this.isLoading = false),
    });

    this.appointmentService.query().subscribe({
      next: (appointments: AppointmentDTO[]) => {
        this.appointments = appointments;
        this.filteredAppointments = this.appointments;
      },
      error: () => (this.isLoading = false),
      complete: () => (this.isLoading = false),
    });
  }

  updatePagination() {
    const startIndex = (this.page - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.paginatedList = this.fullList.slice(startIndex, endIndex);
  }

  onPageChange(pageNumber: number) {
    this.page = pageNumber;
    this.updatePagination();
  }

  showAllAppointments() {
    this.filteredAppointments = this.appointments;
  }

  showFutureAppointments() {
    this.filteredAppointments = this.appointments.filter(appointment => {
      const appointmentDate = appointment.appointmentDate ? new Date(appointment.appointmentDate) : null;
      return appointmentDate && appointmentDate > new Date();
    });
  }

  showTodayAppointments() {
    const today = new Date().toDateString();
    this.filteredAppointments = this.appointments.filter(appointment => {
      const appointmentDate = appointment.appointmentDate ? new Date(appointment.appointmentDate).toDateString() : null;
      return appointmentDate === today;
    });
  }

  updateOrder(orderId: number) {
    this.router.navigate(['/admin/order-management/' + orderId + '/edit']);
  }

  deleteOrder(orderId: number): void {
    const modalRef = this.modalService.open(OrderManagementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.order = orderId;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
