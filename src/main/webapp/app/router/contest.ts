import LandingPage from '../contest/Home.vue';
import { Authority } from '../shared/security/authority';

export default [
  {
    path: '/contest',
    name: 'LandingPage',
    component: LandingPage,
    meta: {
      noLayout: true,
    },
  },
];
