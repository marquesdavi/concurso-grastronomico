import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import DishService from './dish.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type IDish } from '@/shared/model/dish.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DishDetails',
  setup() {
    const dishService = inject('dishService', () => new DishService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const dish: Ref<IDish> = ref({});

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

    return {
      alertService,
      dish,

      ...dataUtils,

      previousState,
    };
  },
});
