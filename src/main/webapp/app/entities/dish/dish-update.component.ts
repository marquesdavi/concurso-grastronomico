import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import DishService from './dish.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IDish, Dish } from '@/shared/model/dish.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DishUpdate',
  setup() {
    const dishService = inject('dishService', () => new DishService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dish: Ref<IDish> = ref(new Dish());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveDish = async dishId => {
      try {
        const res = await dishService().find(dishId);
        dish.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.dishId) {
      retrieveDish(route.params.dishId);
    }

    const dataUtils = useDataUtils();

    const validations = useValidation();
    const validationRules = {
      title: {
        required: validations.required('This field is required.'),
      },
      description: {
        required: validations.required('This field is required.'),
      },
      image: {},
      restaurant: {
        required: validations.required('This field is required.'),
      },
    };
    const v$ = useVuelidate(validationRules, dish as any);
    v$.value.$validate();

    return {
      dishService,
      alertService,
      dish,
      previousState,
      isSaving,
      currentLanguage,
      ...dataUtils,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.dish.id) {
        this.dishService()
          .update(this.dish)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A Dish is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.dishService()
          .create(this.dish)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A Dish is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
