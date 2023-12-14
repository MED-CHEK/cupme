import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { ToastsContainer } from './toasts-container.component';

@NgModule({
  imports: [BrowserModule, NgbModule],
  declarations: [NgbdToastGlobal, ToastsContainer],
  bootstrap: [NgbdToastGlobal],
})
export class NgbdToastGlobalModule {}
