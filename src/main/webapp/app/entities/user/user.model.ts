export interface IUser {
  id: number;
  login?: string;
}

export class User implements IUser {
  constructor(public id: number, public login: string) {}
}

export function getUserIdentifier(user: IUser): number {
  return user.id;
}

export interface UserDTO {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  sex: string;
  age: number;
  weight: number;
  size: number;
  login: string;
  password: string;
  langKey?: string;
}
