<template>
  <div>
    <h2 id="page-heading" data-cy="DishHeading">
      <span id="dish-heading">Dishes</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'DishCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-dish">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Dish</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && dishes && dishes.length === 0">
      <span>No Dishes found</span>
    </div>
    <div class="table-responsive" v-if="dishes && dishes.length > 0">
      <table class="table table-striped" aria-describedby="dishes">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span>Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span>Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('image')">
              <span>Image</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'image'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('restaurant')">
              <span>Restaurant</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'restaurant'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="dish in dishes" :key="dish.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DishView', params: { dishId: dish.id } }">{{ dish.id }}</router-link>
            </td>
            <td>{{ dish.title }}</td>
            <td>{{ dish.description }}</td>
            <td>
              <a v-if="dish.image" v-on:click="openFile(dish.imageContentType, dish.image)">Open</a>
              <span v-if="dish.image">{{ dish.imageContentType }}, {{ byteSize(dish.image) }}</span>
            </td>
            <td>{{ dish.restaurant }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DishView', params: { dishId: dish.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DishEdit', params: { dishId: dish.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(dish)"
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
        <span id="gastronomicContApp.dish.delete.question" data-cy="dishDeleteDialogHeading">Confirm delete operation</span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-dish-heading">Are you sure you want to delete Dish {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-dish"
            data-cy="entityConfirmDeleteButton"
            v-on:click="removeDish()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
    <div v-show="dishes && dishes.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./dish.component.ts"></script>
