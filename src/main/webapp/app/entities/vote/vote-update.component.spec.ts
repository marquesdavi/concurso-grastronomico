/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import VoteUpdate from './vote-update.vue';
import VoteService from './vote.service';
import AlertService from '@/shared/alert/alert.service';

import CustomerService from '@/entities/customer/customer.service';
import DishService from '@/entities/dish/dish.service';

type VoteUpdateComponentType = InstanceType<typeof VoteUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const voteSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<VoteUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Vote Management Update Component', () => {
    let comp: VoteUpdateComponentType;
    let voteServiceStub: SinonStubbedInstance<VoteService>;

    beforeEach(() => {
      route = {};
      voteServiceStub = sinon.createStubInstance<VoteService>(VoteService);
      voteServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          voteService: () => voteServiceStub,
          customerService: () =>
            sinon.createStubInstance<CustomerService>(CustomerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          dishService: () =>
            sinon.createStubInstance<DishService>(DishService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(VoteUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.vote = voteSample;
        voteServiceStub.update.resolves(voteSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(voteServiceStub.update.calledWith(voteSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        voteServiceStub.create.resolves(entity);
        const wrapper = shallowMount(VoteUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.vote = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(voteServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        voteServiceStub.find.resolves(voteSample);
        voteServiceStub.retrieve.resolves([voteSample]);

        // WHEN
        route = {
          params: {
            voteId: '' + voteSample.id,
          },
        };
        const wrapper = shallowMount(VoteUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.vote).toMatchObject(voteSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        voteServiceStub.find.resolves(voteSample);
        const wrapper = shallowMount(VoteUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
