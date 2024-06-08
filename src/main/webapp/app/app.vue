<template>
  <div id="app">
    <ribbon v-if="!$route.meta.noLayout"></ribbon>
    <div id="app-header" v-if="!$route.meta.noLayout">
      <jhi-navbar></jhi-navbar>
    </div>
    <div class="container-fluid">
      <div v-if="!$route.meta.noLayout" class="card jh-card">
        <router-view></router-view>
      </div>
      <div v-else>
        <router-view></router-view>
      </div>
      <b-modal id="login-page" hide-footer lazy>
        <template #modal-title>
          <span data-cy="loginTitle" id="login-title">Sign in</span>
        </template>
        <login-form></login-form>
      </b-modal>

      <jhi-footer v-if="!$route.meta.noLayout"></jhi-footer>
    </div>
  </div>
</template>

<script lang="ts" src="./app.component.ts"></script>
