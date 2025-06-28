import { Component, OnInit } from '@angular/core';

import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CoreService } from 'src/app/services/core.service';
import { EntityRespone } from 'src/app/model/entity-respone.model';
import { Loging } from 'src/app/model/login.model';
import { LogingResponse } from 'src/app/model/loging-response.model';
import { emailValidator } from 'src/app/components/new-user/new-user.component';
import { SpinnerOverlayService } from 'src/app/services/spinner-overlay.service';
import { NewUser } from 'src/app/model/newUser.model';
import { UserService } from 'src/app/services/user.service';
import { SessionStorageService } from 'src/app/services/session-storage.service';



@Component({
  selector: 'app-loging',
  templateUrl: './loging.component.html',
  styleUrls: ['./loging.component.css']
})
export class LogingComponent implements OnInit {

  profileForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });



  public icon = 'visibility';
  public iconUser = 'visibility';
  public notification: string;
  isNotification = false;
  loguin = new LogingResponse();
  recoveryUser = 'If you forget your password or your user, click to recover your data.';
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private spinnerService: SpinnerOverlayService,
    private userService: UserService,
    private coreService: CoreService,
    private sessionService: SessionStorageService
  ) { }

  ngOnInit(): void { }




  onLogin(): void {
    this.spinnerService.spinnerActive =false;
    this.spinnerService.show();
    const bodyLogin = new Loging();
    bodyLogin.username = this.profileForm.value.username.trim();
    bodyLogin.password = this.profileForm.value.password.trim();

    this.userService.onLogin(bodyLogin).subscribe((users) => {
      let userx: NewUser = this.userService.getUser(users);
      let pass = this.userService.encrypt(bodyLogin.password);

      if (userx !== undefined || userx != null) {
        if (userx.password === pass) {
          let logingResponse = new LogingResponse();
          logingResponse.status = 'Loging';
          logingResponse.username = userx.mail;
          this.sessionService.set('UserSession', logingResponse);
          this.coreService.navigateByUrl('/items/menu/list/detail');
          this.spinnerService.hide();
        } else {
          this.isNotification = true;
        }
      } else {
        this.clearInput();
        this.isNotification = true;
        this.spinnerService.hide();
      }
    }, error => {
      this.clearInput();
      this.isNotification = true;
      this.notification = ' --> The data entered is not corret enter valid data <--';
      this.spinnerService.hide();
    });
  }

  clearInput(): void {
    this.profileForm.get('username').setValue('');
    this.profileForm.get('password').setValue('');
  }

  show(): void {
    this.spinnerService.spinnerActive = false;// cuando se usa un interceptor se una este parametro para controlar el spinner 
    this.spinnerService.show();
  }

  hide(): void {
    this.spinnerService.hide();
    this.spinnerService.spinnerActive = true;
  }

  onKey(): void {
    this.isNotification = false;
  }

  recovery(): void {
    this.clearInput();
    this.router.navigateByUrl('/RecoveryUser');
  }

  recoveryNavegate(row: NewUser): void {
    this.router.navigate(
      ['/RecoveryUser'],
      { queryParams: { id: row } }
    );
  }

  viewUserName(): void {
    const userNameInput = document.getElementById('username');
    if (userNameInput.getAttribute('type') == 'password') {
      userNameInput.setAttribute('type', 'text');
      this.iconUser = 'visibility_off';
    } else {
      userNameInput.setAttribute('type', 'password');
      this.iconUser = 'visibility';
    }
  }


  viewPassword(): void {
    const passwordInput = document.getElementById('password');
    if (passwordInput.getAttribute('type') == 'password') {
      passwordInput.setAttribute('type', 'text');
      this.icon = 'visibility_off';
    } else {
      passwordInput.setAttribute('type', 'password');
      this.icon = 'visibility';
    }
  }


  saveUserTest() {
    this.router.navigateByUrl('/items/menu/list/detail');
    // this.coreService.navigateByUrl('/js/menu');
    // let user = new NewUser();
    // user.userFirsName = "Trailer-Admin"
    // user.userLastName = "22"
    // user.userName = "JS.ORANGE.BLACK@GMAIL.COM"
    // user.mail = "js.orange.black@gmail.com"
    // user.password = "123456789"
    // user.fullName = "Trailer2x"
    // user.userCode = "masterx"
    // user.pregunta = "que longitud tiene el trailer"
    // user.respuesta = "22"

    // let userSend = this.userService.encryptUser(user);
    // this.userService.saveUser(userSend);
  }


  getUserTest() {
    let login = new Loging();
    login.password = "123456";
    login.username = "js.orange.black@gmail.com"
    this.userService.onLogintest(login);
    //  this.userService.getAllUsers();
  }


}
