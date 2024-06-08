/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import VoteDetails from './vote-details.vue';
import VoteService from './vote.service';
import AlertService from '@/shared/alert/alert.service';

type VoteDetailsComponentType = InstanceType<typeof VoteDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const voteSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Vote Management Detail Component', () => {
    let voteServiceStub: SinonStubbedInstance<VoteService>;
    let mountOptions: MountingOptions<VoteDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      voteServiceStub = sinon.createStubInstance<VoteService>(VoteService);

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          voteService: () => voteServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        voteServiceStub.find.resolves(voteSample);
        route = {
          params: {
            voteId: '' + '9fec3727-3421-4967-b213-ba36557ca194',
          },
        };
        const wrapper = shallowMount(VoteDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.vote).toMatchObject(voteSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        voteServiceStub.find.resolves(voteSample);
        const wrapper = shallowMount(VoteDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
