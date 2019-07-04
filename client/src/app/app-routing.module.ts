import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AgreementListComponent } from './agreement-list/agreement-list.component';
import { AgreementFormComponent } from './agreement-form/agreement-form.component';
import { ClientSelectFormComponent } from './client-select-form/client-select-form.component'

const routes: Routes = [
  { path: '', redirectTo: '/agreements', pathMatch: 'full' },
  { path: 'agreements', component: AgreementListComponent },
  { path: 'addagreement', component: AgreementFormComponent },
  // { path: 'findclients', component: ClientSelectFormComponent }
  { path: 'findclients', component: ClientSelectFormComponent, outlet: 'popup' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
