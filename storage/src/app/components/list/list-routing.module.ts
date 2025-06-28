import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuardService } from 'src/app/services/auth-guard-service.service';

import { ListComponent } from './listview/list.component';
import { MaterialsCarListComponent } from './materials-car-list/materials-car-list.component';
import { MaterialsListComponent } from './materials-list/materials-list.component';
import { ToolsListComponent } from './tools-list/tools-list.component';


const routesList: Routes = [
  
  { path: 'detail', component: ListComponent,
  children: [
    { path: 'tools', component: ToolsListComponent , canActivate: [AuthGuardService]},
    { path: 'materials', component: MaterialsListComponent , canActivate: [AuthGuardService] },
    { path: 'materials/view', component: MaterialsCarListComponent , canActivate: [AuthGuardService] },
  ]
  , canActivate: [AuthGuardService]
},
];

@NgModule({
  imports: [RouterModule.forChild(routesList)],
  exports: [RouterModule]
})
export class ListRoutingModule { }
