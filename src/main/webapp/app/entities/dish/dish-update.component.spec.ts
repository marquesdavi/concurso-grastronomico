/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import DishUpdate from './dish-update.vue';
import DishService from './dish.service';
import AlertService from '@/shared/alert/alert.service';

type DishUpdateComponentType = InstanceType<typeof DishUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const dishSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<DishUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Dish Management Update Component', () => {
    let comp: DishUpdateComponentType;
    let dishServiceStub: SinonStubbedInstance<DishService>;

    beforeEach(() => {
      route = {};
      dishServiceStub = sinon.createStubInstance<DishService>(DishService);
      dishServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          dishService: () => dishServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(DishUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.dish = dishSample;
        dishServiceStub.update.resolves(dishSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dishServiceStub.update.calledWith(dishSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        dishServiceStub.create.resolves(entity);
        const wrapper = shallowMount(DishUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.dish = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dishServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        dishServiceStub.find.resolves(dishSample);
        dishServiceStub.retrieve.resolves([dishSample]);

        // WHEN
        route = {
          params: {
            dishId: '' + dishSample.id,
          },
        };
        const wrapper = shallowMount(DishUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.dish).toMatchObject(dishSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        dishServiceStub.find.resolves(dishSample);
        const wrapper = shallowMount(DishUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
