import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import VoteService from './vote.service';
import { type IVote } from '@/shared/model/vote.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'VoteDetails',
  setup() {
    const voteService = inject('voteService', () => new VoteService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const vote: Ref<IVote> = ref({});

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

    return {
      alertService,
      vote,

      previousState,
    };
  },
});
