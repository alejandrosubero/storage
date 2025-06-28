import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { LogingResponse } from '../model/loging-response.model';


@Injectable({
  providedIn: 'root'
})
export class SessionStorageServiceService {

  sessionStorgaeModel: LogingResponse = new LogingResponse();

  constructor(private router: Router) { }

  public set(key: string, value: any): void {
    this.sessionStorgaeModel[key] = value;
  }

  get(key: string): any {
    return this.sessionStorgaeModel[key];
  }

  getAll(): LogingResponse {
    return this.sessionStorgaeModel;
  }

  remove(key: string): void {
    this.sessionStorgaeModel[key] = null;
  }

  clear(): void{
    this.sessionStorgaeModel = new LogingResponse();
  }

  checkSeccion(): void {
    if (this.sessionStorgaeModel.status === undefined || this.sessionStorgaeModel.status == null) {
      this.clear();
      this.router.navigateByUrl('/login');
    }
  }



}
