import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuardService } from '../services/auth-guard-service.service';
import { ItemEditNewComponent } from './item-edit-new/item-edit-new.component';
import { ListComponent } from './list/listview/list.component';
import { MenuHomeComponent } from './menu-home/menu-home.component';
import { NewUserComponent } from './new-user/new-user.component';
import { UsersListComponent } from './users-list/users-list.component';



const routes: Routes = [
  { path: 'menu', component: MenuHomeComponent,
  children: [
    { path: 'users', component: UsersListComponent, canActivate: [AuthGuardService] },
    { path: 'newuser', component: NewUserComponent, canActivate: [AuthGuardService] },
    { path: 'item-new-edit', component: ItemEditNewComponent, canActivate: [AuthGuardService]},
    {path: 'list', loadChildren: () => import('../components/list/list.module').then(mod => mod.ListModule)},
   ], canActivate: [AuthGuardService]},

  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ComponetRoutingModule { }