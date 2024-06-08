import { defineComponent, provide } from 'vue';

import DishService from './dish/dish.service';
import CustomerService from './customer/customer.service';
import VoteService from './vote/vote.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('dishService', () => new DishService());
    provide('customerService', () => new CustomerService());
    provide('voteService', () => new VoteService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
