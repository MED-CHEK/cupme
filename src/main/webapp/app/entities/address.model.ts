import { User } from './user/user.model';

export interface Address {
  id?: number;
  city: string;
  country: string;
  address?: string;
  postalCode: string;
  type: string;
  user: User;
}
