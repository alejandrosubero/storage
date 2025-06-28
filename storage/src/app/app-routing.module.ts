import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LogingComponent } from './core/loging/loging.component';
import { RecoveryUserDataComponent } from './core/recovery-user-data/recovery-user-data.component';

const routes: Routes = [
  { path: '', component: LogingComponent },
  { path: 'login', component: LogingComponent},
  { path: 'RecoveryUser', component: RecoveryUserDataComponent},
  { path: 'items', loadChildren: () => import('./components/componet.module').then(m => m.ComponetModule) },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
