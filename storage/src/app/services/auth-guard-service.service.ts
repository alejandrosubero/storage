import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LogingResponse } from '../model/loging-response.model';

import { CoreService } from './core.service';
import { SessionStorageService } from './session-storage.service';


@Injectable()
export class AuthGuardService implements CanActivate {

  seccion: LogingResponse;
  count = 0;
  closeSugurity = 0;


  constructor(private router: Router, private coreService: CoreService, private SessionService: SessionStorageService) { }

  // tslint:disable-next-line: max-line-length
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    this.seccion = this.SessionService.get('UserSession');
    if (this.seccion == null || this.seccion === undefined) {
      this.router.navigate(['/login'], { queryParams: { retUrl: route.url } });
      alert('You are not allowed to view this page. You are redirected to login Page');
      return false;
    }
    return true;
  }



}


