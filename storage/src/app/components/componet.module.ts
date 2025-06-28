import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { MaterialModule } from '../materialModule/materialModule';
import { MenuHomeComponent } from './menu-home/menu-home.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';

import { ComponetRoutingModule } from './componet-routin.module';
import { BarraNavegacionComponent } from './barra-navegacion/barra-navegacion.component';
import { FooterComponent } from './footer/footer.component';
import { NewUserComponent } from './new-user/new-user.component';
import { UsersListComponent } from './users-list/users-list.component';
import { ItemEditNewComponent } from './item-edit-new/item-edit-new.component';
import { AuthGuardService } from '../services/auth-guard-service.service';




@NgModule({
  declarations: [
    MenuHomeComponent,
    BarraNavegacionComponent,
    FooterComponent,
    NewUserComponent,
    UsersListComponent,
    ItemEditNewComponent,
  ],
  imports: [
    CommonModule,
    NgbModule,
    MaterialModule,
    RouterModule,
    ComponetRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    AuthGuardService,
  ],
})
export class ComponetModule { }