<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="gastronomicContApp.customer.home.createOrEditLabel" data-cy="CustomerCreateUpdateHeading">Create or edit a Customer</h2>
        <div>
          <div class="form-group" v-if="customer.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="customer.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="customer-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="customer-email"
              data-cy="email"
              :class="{ valid: !v$.email.$invalid, invalid: v$.email.$invalid }"
              v-model="v$.email.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="customer-phonenumber">Phonenumber</label>
            <input
              type="text"
              class="form-control"
              name="phonenumber"
              id="customer-phonenumber"
              data-cy="phonenumber"
              :class="{ valid: !v$.phonenumber.$invalid, invalid: v$.phonenumber.$invalid }"
              v-model="v$.phonenumber.$model"
              required
            />
            <div v-if="v$.phonenumber.$anyDirty && v$.phonenumber.$invalid">
              <small class="form-text text-danger" v-for="error of v$.phonenumber.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="customer-cpf">Cpf</label>
            <input
              type="text"
              class="form-control"
              name="cpf"
              id="customer-cpf"
              data-cy="cpf"
              :class="{ valid: !v$.cpf.$invalid, invalid: v$.cpf.$invalid }"
              v-model="v$.cpf.$model"
              required
            />
            <div v-if="v$.cpf.$anyDirty && v$.cpf.$invalid">
              <small class="form-text text-danger" v-for="error of v$.cpf.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
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
<script lang="ts" src="./customer-update.component.ts"></script>
