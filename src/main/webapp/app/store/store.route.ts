import { Route } from '@angular/router';

import { StoreComponent } from './store.component';

export const STORE_ROUTE: Route = {
  path: '',
  component: StoreComponent,
  data: {
    pageTitle: 'global.store.title',
  },
};
