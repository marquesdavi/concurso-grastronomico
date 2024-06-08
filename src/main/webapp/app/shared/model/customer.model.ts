export interface ICustomer {
  id?: number;
  email?: string | null;
  phonenumber?: string;
  cpf?: string;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public email?: string | null,
    public phonenumber?: string,
    public cpf?: string,
  ) {}
}
