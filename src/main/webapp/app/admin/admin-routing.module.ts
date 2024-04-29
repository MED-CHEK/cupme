import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

@NgModule({
  imports: [
    /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    RouterModule.forChild([
      {
        path: 'user-management',
        loadChildren: () => import('./user-management/user-management.module').then(m => m.UserManagementModule),
        data: {
          pageTitle: 'userManagement.home.title',
        },
      },
      {
        path: 'product-management',
        loadChildren: () => import('./product-management/product-management.module').then(m => m.ProductManagementModule),
        data: {
          pageTitle: 'productManagement.home.title',
        },
      },
      {
        path: 'protocol-management',
        loadChildren: () => import('./protocol-management/protocol-management.module').then(m => m.ProtocolManagementModule),
        data: {
          pageTitle: 'rotocolManagement.home.title',
        },
      },
      {
        path: 'order-management',
        loadChildren: () => import('./order-management/order-management.module').then(m => m.OrderManagementModule),
        data: {
          pageTitle: 'rotocolManagement.home.title',
        },
      },
      {
        path: 'promo-code-management',
        loadChildren: () => import('./promo-code-management/promo-code-management.module').then(m => m.PromoCodeManagementModule),
        data: {
          pageTitle: 'rotocolManagement.home.title',
        },
      },
      {
        path: 'docs',
        loadChildren: () => import('./docs/docs.module').then(m => m.DocsModule),
      },
      {
        path: 'configuration',
        loadChildren: () => import('./configuration/configuration.module').then(m => m.ConfigurationModule),
      },
      {
        path: 'health',
        loadChildren: () => import('./health/health.module').then(m => m.HealthModule),
      },
      {
        path: 'logs',
        loadChildren: () => import('./logs/logs.module').then(m => m.LogsModule),
      },
      {
        path: 'metrics',
        loadChildren: () => import('./metrics/metrics.module').then(m => m.MetricsModule),
      },
      /* jhipster-needle-add-admin-route - JHipster will add admin routes here */
    ]),
  ],
})
export class AdminRoutingModule {}
