<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="vote">
        <h2 class="jh-entity-heading" data-cy="voteDetailsHeading"><span>Vote</span> {{ vote.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Customer</span>
          </dt>
          <dd>
            <div v-if="vote.customer">
              <router-link :to="{ name: 'CustomerView', params: { customerId: vote.customer.id } }">{{ vote.customer.email }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Dish</span>
          </dt>
          <dd>
            <div v-if="vote.dish">
              <router-link :to="{ name: 'DishView', params: { dishId: vote.dish.id } }">{{ vote.dish.title }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link v-if="vote.id" :to="{ name: 'VoteEdit', params: { voteId: vote.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./vote-details.component.ts"></script>
