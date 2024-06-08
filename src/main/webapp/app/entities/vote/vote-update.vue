<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="gastronomicContApp.vote.home.createOrEditLabel" data-cy="VoteCreateUpdateHeading">Create or edit a Vote</h2>
        <div>
          <div class="form-group" v-if="vote.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="vote.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vote-customer">Customer</label>
            <select class="form-control" id="vote-customer" data-cy="customer" name="customer" v-model="vote.customer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="vote.customer && customerOption.id === vote.customer.id ? vote.customer : customerOption"
                v-for="customerOption in customers"
                :key="customerOption.id"
              >
                {{ customerOption.email }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vote-dish">Dish</label>
            <select class="form-control" id="vote-dish" data-cy="dish" name="dish" v-model="vote.dish">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="vote.dish && dishOption.id === vote.dish.id ? vote.dish : dishOption"
                v-for="dishOption in dishes"
                :key="dishOption.id"
              >
                {{ dishOption.title }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./vote-update.component.ts"></script>
