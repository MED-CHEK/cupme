import { Time } from '@angular/common';
import { UserDTO } from './user/user.model';
import { OrderDTO } from './order.model';

export interface SessionDTO {
  id: number;
  name: string;
  duration: number;
  price: number;
}

export interface AppointmentDTO {
  id: number;
  email: string;
  telephone: string;
  type: ContactType;
  appointmentDate: string | null;
  appointmentTime: string;
  session: SessionDTO;
  order?: OrderDTO;
}

export interface AppointmentInfo {
  email: string;
  telephone: string;
  type: ContactType;
  appointmentDate: string;
  appointmentTime: string;
}

export enum ContactType {
  MAIL = 'MAIL',
  WHATSAPP = 'WHATSAPP',
}
