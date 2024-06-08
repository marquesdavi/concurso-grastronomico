<template>
  <div>
    <h2 id="page-heading" data-cy="VoteHeading">
      <span id="vote-heading">Votes</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'VoteCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-vote">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Vote</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && votes && votes.length === 0">
      <span>No Votes found</span>
    </div>
    <div class="table-responsive" v-if="votes && votes.length > 0">
      <table class="table table-striped" aria-describedby="votes">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customer.email')">
              <span>Customer</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'customer.email'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dish.title')">
              <span>Dish</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dish.title'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="vote in votes" :key="vote.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'VoteView', params: { voteId: vote.id } }">{{ vote.id }}</router-link>
            </td>
            <td>
              <div v-if="vote.customer">
                <router-link :to="{ name: 'CustomerView', params: { customerId: vote.customer.id } }">{{
                  vote.customer.email
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="vote.dish">
                <router-link :to="{ name: 'DishView', params: { dishId: vote.dish.id } }">{{ vote.dish.title }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'VoteView', params: { voteId: vote.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'VoteEdit', params: { voteId: vote.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(vote)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="gastronomicContApp.vote.delete.question" data-cy="voteDeleteDialogHeading">Confirm delete operation</span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-vote-heading">Are you sure you want to delete Vote {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-vote"
            data-cy="entityConfirmDeleteButton"
            v-on:click="removeVote()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
    <div v-show="votes && votes.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./vote.component.ts"></script>
