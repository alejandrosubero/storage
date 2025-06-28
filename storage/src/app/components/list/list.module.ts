import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialsListComponent } from './materials-list/materials-list.component';
import { ToolsListComponent } from './tools-list/tools-list.component';
import { MaterialsCarListComponent } from './materials-car-list/materials-car-list.component';
import { RouterModule } from '@angular/router';
import { MaterialModule } from 'src/app/materialModule/materialModule';
import { ListComponent } from './listview/list.component';
import { ListRoutingModule } from './list-routing.module';
import { ItensListComponent } from '../itens-list/itens-list.component';
import { AuthGuardService } from 'src/app/services/auth-guard-service.service';



@NgModule({
  declarations: [
    MaterialsListComponent,
    ToolsListComponent,
    MaterialsCarListComponent,
    ListComponent,
    ItensListComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule,
    ListRoutingModule
  ],
   schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    AuthGuardService,
  ],
})
export class ListModule { }
