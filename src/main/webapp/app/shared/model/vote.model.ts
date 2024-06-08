import { type ICustomer } from '@/shared/model/customer.model';
import { type IDish } from '@/shared/model/dish.model';

export interface IVote {
  id?: string;
  customer?: ICustomer | null;
  dish?: IDish | null;
}

export class Vote implements IVote {
  constructor(
    public id?: string,
    public customer?: ICustomer | null,
    public dish?: IDish | null,
  ) {}
}
