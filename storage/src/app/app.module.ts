import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LogingComponent } from './core/loging/loging.component';

import { ItemEditNewComponent } from './components/item-edit-new/item-edit-new.component';
import { MaterialModule } from './materialModule/materialModule';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FiverbaseConextionService } from './services/fiverbase-conextion.service';
import { SpinnerComponent } from './core/spinner/spinner.component';
import { SpinnerOverlayComponent } from './core/spinner-overlay/spinner-overlay.component';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PortalModule } from '@angular/cdk/portal';
import { NewUserComponent } from './components/new-user/new-user.component';
import { RecoveryUserDataComponent } from './core/recovery-user-data/recovery-user-data.component';
import { SessionStorageService } from './services/session-storage.service';
import { SessionStorageServiceService } from './services/session-storage-service.service';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';
import { AuthGuardService } from './services/auth-guard-service.service';
import { SpinnerOverlayService } from './services/spinner-overlay.service';
import { AuthInterceptorService } from './services/auth-interceptor.service';


@NgModule({
  declarations: [
    AppComponent,
    LogingComponent,
    RecoveryUserDataComponent, 
    SpinnerComponent,
    SpinnerOverlayComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    MaterialModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    PortalModule,
    RouterModule
  ],
  
  entryComponents: [
    SpinnerOverlayComponent,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    AuthGuardService,
    FiverbaseConextionService,
    SessionStorageService,
    SessionStorageServiceService,
    SpinnerOverlayService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }


