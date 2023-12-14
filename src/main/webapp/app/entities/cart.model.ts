import { User } from './user/user.model';

export interface CartDTO {
  id?: number;
  user: User;
}
