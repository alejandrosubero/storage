import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { NewUser } from 'src/app/model/newUser.model';
import { UserService } from 'src/app/services/user.service';





// tslint:disable-next-line: typedef
export function emailValidator(control: AbstractControl) {
  var EMAIL_REGEXP = /^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$/i;
  if (!EMAIL_REGEXP.test(control.value)) {
    return { invalidEmail: true };
  }
  return null;
}


@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css']
})
export class NewUserComponent implements OnInit {

  profileForm = this.fb.group({
    userFirsName: ['', Validators.required],
    userLastName: ['', Validators.required],
    mail: ['', [Validators.required, emailValidator]],
    userName: ['', Validators.required],
    password: ['', Validators.required],
    pregunta: ['', Validators.required],
    respuesta: ['', Validators.required],
  });

  public icon = 'visibility';
  public iconUser = 'visibility';
  public notification: string;
  isNotification = false;
  haveKey = false;
  button = 'Save User';


  constructor(
    private fb: FormBuilder,
    private router: Router,
    private _snackBar: MatSnackBar,
    private userService: UserService,
    private route: ActivatedRoute,) {
      this.clearInput();
     }

  ngOnInit(): void {
    
    this.route.queryParams.forEach(params => {
      
      if (params.id !== undefined || params.id != null) {
        let newUser = new NewUser();
        newUser = JSON.parse(params.id);
        this.clearInput();
        this.setInput(newUser);
        if (newUser.key !== undefined && newUser.key != null) {
          this.haveKey = true;
          this.button = 'Update';
        }
      } else {
        this.clearInput();
      }
    });
  }

  setInput(user: NewUser): void {
    this.profileForm.patchValue({
      userFirsName: user.userFirsName,
      userLastName: user.userLastName,
      mail: user.mail,
      password: '',
      pregunta: user.pregunta,
      respuesta: '',
    });
  }


  clearInput(): void {
    this.profileForm.patchValue({
      userFirsName: '',
      userLastName: '',
      mail: '',
      userName: '',
      password: '',
      pregunta: '',
      respuesta: '',
    });
  }

  sendNewUser(): void {
    if (this.profileForm.valid) {
      const newUser = new NewUser();
      newUser.userFirsName = this.profileForm.value.userFirsName;
      newUser.userLastName = this.profileForm.value.userLastName;
      newUser.mail = this.profileForm.value.mail.trim();
      // newUser.userName = this.profileForm.value.userName.trim();
      newUser.password = this.profileForm.value.password.trim();
      newUser.pregunta = this.profileForm.value.pregunta;
      newUser.respuesta = this.profileForm.value.respuesta;
      const user = this.userService.encryptUser(newUser);

      if (this.haveKey) {
        this.userService.updateUser(user)
      } else {
        // console.log(JSON.stringify(newUser));
        this.userService.saveUser(user);
      }

    }

  }

  openSnackBar(message: string, action: string): void {
    this._snackBar.open(message, action);
  }



  viewUserName(): void {
    const userNameInput = document.getElementById('userName');
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


  viewKeyAcces(): void {
    const passwordInput = document.getElementById('keyAcces');
    if (passwordInput.getAttribute('type') == 'password') {
      passwordInput.setAttribute('type', 'text');
      this.icon = 'visibility_off';
    } else {
      passwordInput.setAttribute('type', 'password');
      this.icon = 'visibility';
    }
  }

  onCancel(): void {
    this.clearInput();
    this.router.navigateByUrl('/items/menu/users');
  }








}
