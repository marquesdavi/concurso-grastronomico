<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="gastronomicContApp.dish.home.createOrEditLabel" data-cy="DishCreateUpdateHeading">Create or edit a Dish</h2>
        <div>
          <div class="form-group" v-if="dish.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="dish.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="dish-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="dish-title"
              data-cy="title"
              :class="{ valid: !v$.title.$invalid, invalid: v$.title.$invalid }"
              v-model="v$.title.$model"
              required
            />
            <div v-if="v$.title.$anyDirty && v$.title.$invalid">
              <small class="form-text text-danger" v-for="error of v$.title.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="dish-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="dish-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
              required
            />
            <div v-if="v$.description.$anyDirty && v$.description.$invalid">
              <small class="form-text text-danger" v-for="error of v$.description.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="dish-image">Image</label>
            <div>
              <div v-if="dish.image" class="form-text text-danger clearfix">
                <a class="pull-left" v-on:click="openFile(dish.imageContentType, dish.image)">Open</a><br />
                <span class="pull-left">{{ dish.imageContentType }}, {{ byteSize(dish.image) }}</span>
                <button
                  type="button"
                  v-on:click="
                    dish.image = null;
                    dish.imageContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_image" class="btn btn-primary pull-right">Add blob</label>
              <input
                type="file"
                ref="file_image"
                id="file_image"
                style="display: none"
                data-cy="image"
                v-on:change="setFileData($event, dish, 'image', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="image"
              id="dish-image"
              data-cy="image"
              :class="{ valid: !v$.image.$invalid, invalid: v$.image.$invalid }"
              v-model="v$.image.$model"
            />
            <input type="hidden" class="form-control" name="imageContentType" id="dish-imageContentType" v-model="dish.imageContentType" />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="dish-restaurant">Restaurant</label>
            <input
              type="text"
              class="form-control"
              name="restaurant"
              id="dish-restaurant"
              data-cy="restaurant"
              :class="{ valid: !v$.restaurant.$invalid, invalid: v$.restaurant.$invalid }"
              v-model="v$.restaurant.$model"
              required
            />
            <div v-if="v$.restaurant.$anyDirty && v$.restaurant.$invalid">
              <small class="form-text text-danger" v-for="error of v$.restaurant.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./dish-update.component.ts"></script>
