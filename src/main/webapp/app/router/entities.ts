import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Dish = () => import('@/entities/dish/dish.vue');
const DishUpdate = () => import('@/entities/dish/dish-update.vue');
const DishDetails = () => import('@/entities/dish/dish-details.vue');

const Customer = () => import('@/entities/customer/customer.vue');
const CustomerUpdate = () => import('@/entities/customer/customer-update.vue');
const CustomerDetails = () => import('@/entities/customer/customer-details.vue');

const Vote = () => import('@/entities/vote/vote.vue');
const VoteUpdate = () => import('@/entities/vote/vote-update.vue');
const VoteDetails = () => import('@/entities/vote/vote-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'dish',
      name: 'Dish',
      component: Dish,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'dish/new',
      name: 'DishCreate',
      component: DishUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'dish/:dishId/edit',
      name: 'DishEdit',
      component: DishUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'dish/:dishId/view',
      name: 'DishView',
      component: DishDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer',
      name: 'Customer',
      component: Customer,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/new',
      name: 'CustomerCreate',
      component: CustomerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/:customerId/edit',
      name: 'CustomerEdit',
      component: CustomerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/:customerId/view',
      name: 'CustomerView',
      component: CustomerDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vote',
      name: 'Vote',
      component: Vote,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vote/new',
      name: 'VoteCreate',
      component: VoteUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vote/:voteId/edit',
      name: 'VoteEdit',
      component: VoteUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vote/:voteId/view',
      name: 'VoteView',
      component: VoteDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
