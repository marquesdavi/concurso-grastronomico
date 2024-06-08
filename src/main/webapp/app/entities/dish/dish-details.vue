<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="dish">
        <h2 class="jh-entity-heading" data-cy="dishDetailsHeading"><span>Dish</span> {{ dish.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Title</span>
          </dt>
          <dd>
            <span>{{ dish.title }}</span>
          </dd>
          <dt>
            <span>Description</span>
          </dt>
          <dd>
            <span>{{ dish.description }}</span>
          </dd>
          <dt>
            <span>Image</span>
          </dt>
          <dd>
            <div v-if="dish.image">
              <a v-on:click="openFile(dish.imageContentType, dish.image)">Open</a>
              {{ dish.imageContentType }}, {{ byteSize(dish.image) }}
            </div>
          </dd>
          <dt>
            <span>Restaurant</span>
          </dt>
          <dd>
            <span>{{ dish.restaurant }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link v-if="dish.id" :to="{ name: 'DishEdit', params: { dishId: dish.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./dish-details.component.ts"></script>
