import { Route } from '@angular/router';

import { SuccessComponent } from './success.component';

export const SUCCESS_ROUTE: Route = {
  path: '',
  component: SuccessComponent,
  data: {
    pageTitle: 'global.success.title',
  },
};
