import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import VoteService from './vote.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CustomerService from '@/entities/customer/customer.service';
import { type ICustomer } from '@/shared/model/customer.model';
import DishService from '@/entities/dish/dish.service';
import { type IDish } from '@/shared/model/dish.model';
import { type IVote, Vote } from '@/shared/model/vote.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'VoteUpdate',
  setup() {
    const voteService = inject('voteService', () => new VoteService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const vote: Ref<IVote> = ref(new Vote());

    const customerService = inject('customerService', () => new CustomerService());

    const customers: Ref<ICustomer[]> = ref([]);

    const dishService = inject('dishService', () => new DishService());

    const dishes: Ref<IDish[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveVote = async voteId => {
      try {
        const res = await voteService().find(voteId);
        vote.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.voteId) {
      retrieveVote(route.params.voteId);
    }

    const initRelationships = () => {
      customerService()
        .retrieve()
        .then(res => {
          customers.value = res.data;
        });
      dishService()
        .retrieve()
        .then(res => {
          dishes.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      customer: {},
      dish: {},
    };
    const v$ = useVuelidate(validationRules, vote as any);
    v$.value.$validate();

    return {
      voteService,
      alertService,
      vote,
      previousState,
      isSaving,
      currentLanguage,
      customers,
      dishes,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.vote.id) {
        this.voteService()
          .update(this.vote)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A Vote is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.voteService()
          .create(this.vote)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A Vote is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
