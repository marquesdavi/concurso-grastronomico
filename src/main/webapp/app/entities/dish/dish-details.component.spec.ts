/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import DishDetails from './dish-details.vue';
import DishService from './dish.service';
import AlertService from '@/shared/alert/alert.service';

type DishDetailsComponentType = InstanceType<typeof DishDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const dishSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Dish Management Detail Component', () => {
    let dishServiceStub: SinonStubbedInstance<DishService>;
    let mountOptions: MountingOptions<DishDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      dishServiceStub = sinon.createStubInstance<DishService>(DishService);

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
          dishService: () => dishServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        dishServiceStub.find.resolves(dishSample);
        route = {
          params: {
            dishId: '' + 123,
          },
        };
        const wrapper = shallowMount(DishDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.dish).toMatchObject(dishSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        dishServiceStub.find.resolves(dishSample);
        const wrapper = shallowMount(DishDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
