import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Loging } from '../model/login.model';
import { NewUser } from '../model/newUser.model';

import { AESEncryptDecryptService } from './aesencrypt-decrypt-service.service';
import { FiverbaseConextionService } from './fiverbase-conextion.service';
import { SpinnerOverlayService } from './spinner-overlay.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseUsersRef = environment.baseUsersRef
  urlUserBase = environment.urlUserBase;
  urlBaseDeleteUsers = environment.urlBaseDeleteUsers;
  userList: NewUser[] = [];

  private estimateList2 = new BehaviorSubject<Array<NewUser>>(new Array<NewUser>());
  em2 = this.estimateList2.asObservable();


  constructor(
    public snackBar: MatSnackBar,
    protected http: HttpClient,
    private encryptService: AESEncryptDecryptService,
    private fire: FiverbaseConextionService,
    private router: Router,
    // private spinnerService: SpinnerOverlayService,
    ) { }

    navigateByUrl(ruta: string): void {
      this.router.navigateByUrl(ruta);
    }

  updateUserList2(newListTablet: NewUser[]): void {
    this.estimateList2.next(newListTablet);
  }

  // tslint:disable-next-line: typedef
  findEstimate(id: any) {
    let lista: Array<NewUser> = new Array<NewUser>();
    this.em2.subscribe(list => {
      lista = list.filter(x => x.key == id);
    });
    return lista;
  }

  onLogin(loginForm: Loging): Observable<any> {
    const url = `${this.urlUserBase}${this.baseUsersRef}`;
    return this.http.get(url, { params: new HttpParams().set('orderBy', '"mail"').set('equalTo', `"${loginForm.username}"`), });
  }


  onLoginO(loginForm: Loging): any {
    let notification = false;
    const url = `${this.urlUserBase}${this.baseUsersRef}`;
    this.http.get(url,
      {
        params: new HttpParams().set('orderBy', '"mail"').set('equalTo', `"${loginForm.username}"`),
      }).subscribe((users) => {

        let userx: NewUser = this.getUser(users);
        console.log(userx);
        let pass = this.encrypt(loginForm.password);

        if (userx !== undefined || userx != null) {
          if (userx.password === pass) {
            console.log("login use susseces full", userx);
            this.navigateByUrl('/list');
          } else {
            notification = true;
          }
        }
        return notification;
      });
  }

  onLogintest(loginForm: Loging): any {
    const url = `${this.urlUserBase}${this.baseUsersRef}`;
    this.http.get(url,
      {
        params: new HttpParams().set('orderBy', '"mail"').set('equalTo', `"${loginForm.username}"`),
      }).subscribe((users) => {
        let userx: NewUser = this.getUser(users);
        // console.log(userx);

        return userx;
      });
  }

  saveUser(body: any) {
    // this.spinnerService.spinnerActive = false;
    // this.spinnerService.show();
    const url = `${this.urlUserBase}${this.baseUsersRef}`;
    this.fire.save(body, url).subscribe(postResponse => {
      // console.log('postResponse: ', postResponse);
      this.navigateByUrl('/items/menu/users');
      // this.spinnerService.hide();
    });;
  }


  deleteUser(body: any) {
    // this.spinnerService.spinnerActive = false;
    // this.spinnerService.show();
    const url = `${this.urlBaseDeleteUsers}`;
    this.fire.delete(body, url).subscribe(postResponse => {
      // console.log('postResponse: ', postResponse);
      this.navigateByUrl('/items/menu/users');
      this.getAllUsers();
      // this.spinnerService.hide();
    });;
  }

  getUser(res: any): NewUser {
    if (res) {
      const processedOrders: NewUser[] = [];
      for (const [key, value] of Object.entries(res)) {
        const valset = value as NewUser;
        valset.key = key;
        processedOrders.push(valset);
      }
      return processedOrders[0];
    }
  }

  updateUser(item:NewUser ) {
    // this.spinnerService.spinnerActive = false;
    // this.spinnerService.show();
    const url = `${this.urlUserBase}${this.baseUsersRef}`;
    this.fire.update(item, url).subscribe(res=>{
      // console.log(res);
      this.navigateByUrl('/items/menu/users');
      // this.spinnerService.hide();
    });

  }

  getAllUsers(): void {
    // this.spinnerService.spinnerActive = false;
    // this.spinnerService.show();
    const url = `${this.urlUserBase}${this.baseUsersRef}`;
    this.fire.getAll(url).subscribe((res) => {
      if (res) {
        const processedOrders: NewUser[] = [];

        for (const [key, value] of Object.entries(res)) {
          const valset = value as NewUser;
          valset.key = key;
          processedOrders.push(valset);
          this.updateUserList2(processedOrders);
        }
        // console.log(processedOrders);
        this.userList = processedOrders;
        // this.spinnerService.hide();
      }
    }, (error: any) => {
      this.snackBar.open('FAILL THE RESPONSE OFF SERVER', 'Cerrar');
      // this.spinnerService.hide();
    });
  }



  encryptUser(user: NewUser): NewUser {

    if (user !== undefined && user != null) {

      if (user.password !== undefined && user.password != null) {
        const data = this.encrypt(user.password);
        user.password = data;
      }


      if (user.respuesta !== undefined && user.respuesta != null) {
        const data = this.encrypt(user.respuesta);
        user.respuesta = data;
      }

    }
    return user;
  }

  encryptUser2(user: NewUser): NewUser {
    if (user !== undefined && user != null) {
      if (user.userFirsName !== undefined && user.userFirsName != null) {
        const data = this.encrypt(user.userFirsName);
        user.userFirsName = data;
      }
      if (user.userLastName !== undefined && user.userLastName != null) {
        const data = this.encrypt(user.userLastName);
        user.userLastName = data;
      }
      if (user.userName !== undefined && user.userName != null) {
        const data = this.encrypt(user.userName);
        user.userName = data;
      }
      if (user.password !== undefined && user.password != null) {
        const data = this.encrypt(user.password);
        user.password = data;
      }
      if (user.mail !== undefined && user.mail != null) {
        const data = this.encrypt(user.mail);
        user.mail = data;
      }

      if (user.respuesta !== undefined && user.respuesta != null) {
        const data = this.encrypt(user.respuesta);
        user.respuesta = data;
      }

      if (user.pregunta !== undefined && user.pregunta != null) {
        const data = this.encrypt(user.pregunta);
        user.pregunta = data;
      }

      if (user.imagen !== undefined && user.imagen != null) {
        const data = this.encrypt(user.imagen);
        user.imagen = data;
      }

    }
    return user;
  }


  // private show(): void {
  //   this.spinnerService.spinnerActive = false;// cuando se usa un interceptor se una este parametro para controlar el spinner 
  //   this.spinnerService.show();
  // }

  // private hide(): void {
  //   this.spinnerService.hide();
  //   this.spinnerService.spinnerActive = true;
  // }

  encrypt(data): string {
    return this.encryptService.encripted(data);
  }


  decrypt(dataEcrypted): string {
    return this.encryptService.decrypted(dataEcrypted);
  }
}
