import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';

import { Subject } from 'rxjs';
import { LogingResponse } from 'src/app/model/loging-response.model';
import { UserRecovery } from 'src/app/model/UserRecovery.model';
import { CoreService } from 'src/app/services/core.service';
import { UserService } from 'src/app/services/user.service';

import { SessionStorageService } from 'src/app/services/session-storage.service';
import { takeUntil } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Loging } from 'src/app/model/login.model';
import { NewUser } from 'src/app/model/newUser.model';


// tslint:disable-next-line: typedef
export function emailValidator(control: AbstractControl) {
  var EMAIL_REGEXP = /^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$/i;
  if (!EMAIL_REGEXP.test(control.value)) {
    return { invalidEmail: true };
  }
  return null;
}

@Component({
  selector: 'app-recovery-user-data',
  templateUrl: './recovery-user-data.component.html',
  styleUrls: ['./recovery-user-data.component.css']
})
export class RecoveryUserDataComponent implements OnInit {

  userRecovery: UserRecovery = new UserRecovery();
  loguin = new LogingResponse();
  public notification: string;
  isNotification = false;
  infoText = 'To recover the password or the user, put the mail:';
  questionRecive = false;
  title = 'Recovery user Data';

  mail: string;
  pregunta: string;
  respuesta: string;
  private present$ = new Subject<void>();
  userx: NewUser;


  constructor(
    private userService: UserService,
    private coreService: CoreService,
    private sessionService: SessionStorageService
  ) { }

  ngOnInit(): void {
    this.userx = new NewUser();
  }


  setInput(recovery: UserRecovery): void {
    this.mail = recovery.mail;
    this.pregunta = recovery.pregunta;
    this.respuesta = recovery.respuesta;
  }


  onSend(): void {
    const bodyLogin = new Loging();
    bodyLogin.username = this.mail.trim();
    bodyLogin.password = '';

    this.userService.onLogin(bodyLogin).subscribe((users) => {
      this.userx = this.userService.getUser(users);
      if (this.userx.pregunta != null && this.userx.pregunta != "" && this.userx.respuesta != null) {
        this.pregunta = this.userx.pregunta;
        this.questionRecive = true;
      }
    }, error => {
      this.clearInput();
      this.isNotification = true;
      this.notification = ' --> The data entered is not corret enter valid data <--';
    });
  }


  onRecovery(): void {
    let respuestax = this.userService.encrypt(this.respuesta);
  
    if (this.userx.respuesta === respuestax ) {
      let logingResponse = new LogingResponse();
      logingResponse.status = 'Loging';
      logingResponse.username = this.userx.mail;

      this.sessionService.set('UserSession', logingResponse);
      this.coreService.navigateByUrl('/items/menu/list/detail');
    }else{
      this.clearInput();
     this.onCancel();
    }
  }

  onCancel(): void {
    this.coreService.navigateByUrl('/login');
  }

  clearInput(): void {
    this.mail = '';
    this.pregunta = '';
    this.respuesta = '';
  }


}
