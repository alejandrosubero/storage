import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ThemePalette } from '@angular/material/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Router, ActivatedRoute } from '@angular/router';
import { Data, DateSave } from 'src/app/model/data.model';
import { CoreService } from 'src/app/services/core.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-item-edit-new',
  templateUrl: './item-edit-new.component.html',
  styleUrls: ['./item-edit-new.component.css']
})
export class ItemEditNewComponent implements OnInit {


  profileForm = this.fb.group({
    category: [''],
    itemName: ['', Validators.required],
    storeArea: ['', Validators.required],
    unit: ['', Validators.required],
    alertAmounts: [''],
    itemDescription: [''],
    itemNumber: [''],
    itemType: [''],
    itemUse: [''],
    showAlert: [''],
    statusCloud: [''],
    storeSecction: [''],
    storeType: [''],
    type: [''],
    uid: [''],
    key: [''],
    itemImageString: [''],
  });

  newItem: Data = new Data()
  haveKey = false;
  button = 'save';
  craftImagePreview = 'assets/images/\download.png';

  checkedTool = true;
  indeterminateTool = false;
  labelPositionTool: 'before' | 'after' = 'after';
  labelPositionTool2: 'before' | 'after' = 'before';
  disabledTool = false;

  checked = false;
  indeterminate = false;
  labelPosition: 'before' | 'after' = 'after';
  disabled = false;
  color: ThemePalette = 'primary';
  categories = ['Variable', 'Fixed'];
  selected = 'Variable';
  showAlertcheck = false;
  fileData: File = null;
  ing: SafeResourceUrl;
  viewSafeResourceUrl = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private _snackBar: MatSnackBar,
    private userService: UserService,
    private route: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private coreservice: CoreService) {
    this.selected = this.categories[1];
  }

  ngOnInit(): void {
    this.route.queryParams.forEach(params => {
      if (params.id !== undefined || params.id != null) {
        this.newItem = new Data();
        this.newItem = JSON.parse(params.id);
        if (this.newItem.key !== undefined && this.newItem.key != null) {
          // console.log('newItem', this.newItem);
          this.haveKey = true;
          this.button = 'Update';

          if (this.newItem.itemImageString == '') {
            this.ing = this.sanitizer.bypassSecurityTrustResourceUrl(`data:image/jpg;base64,${this.newItem.itemImageBase64String}`);
            this.viewSafeResourceUrl = true;
          } else {
            let buffer = JSON.parse(this.newItem.itemImageString);
            let base64Img = this.arrayBufferToBase64(buffer.itemImage);
            this.ing = this.sanitizer.bypassSecurityTrustResourceUrl(`data:image/jpg;base64,${base64Img}`);
            this.viewSafeResourceUrl = true;
          }


          if ( this.newItem.type === 'Material') {
            this.checked = true;
            this.checkedTool = false;
          }

          if (  this.newItem.type === 'Tool') {
            this.checkedTool = true;
            this.checked = false;
          }

          this.setInput(this.newItem);
        }
      } else {
        this.newItem = new Data();
        this.newItem.category = this.categories[0];
        this.newItem.showAlert = false;
        this.newItem.itemNumber = '1';
        this.clearInput();
        this.setInput(this.newItem);
      }
    });
  }


  arrayBufferToBase64(buffer): any {
    var binary = '';
    var bytes = new Uint8Array(buffer);
    var len = bytes.byteLength;
    for (var i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    return window.btoa(binary);
  }


  setInput(data: Data): void {
    this.profileForm.patchValue({
      alertAmounts: data.alertAmounts,
      category: data.category,
      itemDescription: data.itemDescription,
      itemName: data.itemName,
      itemNumber: data.itemNumber,
      itemType: data.itemType,
      itemUse: data.itemUse,
      showAlert: data.showAlert,
      statusCloud: data.statusCloud,
      storeArea: data.storeArea,
      storeSecction: data.storeSecction,
      storeType: data.storeType,
      type: data.type,
      uid: data.uid,
      unit: data.unit,
      key: data.key,
      itemImageString: data.itemImageString,
    });
  }


  clearInput(): void {
    this.profileForm.patchValue({
      alertAmounts: '',
      category: '',
      itemDescription: '',
      itemName: '',
      itemNumber: '',
      itemType: '',
      itemUse: '',
      showAlert: '',
      statusCloud: '',
      storeArea: '',
      storeSecction: '',
      storeType: '',
      type: '',
      uid: '',
      unit: '',
      key: '',
    });
  }


  openSnackBar(message: string, action: string): void {
    this._snackBar.open(message, action);
  }


  onCancel(): void {
    this.clearInput();
    this.router.navigateByUrl('/items/menu/list/detail');
  }

  sendNew(): void {
    this.save();
  }



  save(): void {

    if (this.profileForm.valid) {

      this.newItem.itemName = this.profileForm.value.itemName;
      this.newItem.itemNumber = `${this.profileForm.value.itemNumber}`;
      this.newItem.itemDescription = this.profileForm.value.itemDescription;
      this.newItem.itemType = this.profileForm.value.itemType;
      this.newItem.itemUse = this.profileForm.value.itemUse;
      this.newItem.itemImageString = '';

      // tslint:disable-next-line: max-line-length
      if (this.profileForm.value.itemNumber !== '' && this.profileForm.value.itemNumber != null && this.profileForm.value.itemNumber !== undefined) {
        this.newItem.alertAmounts = this.profileForm.value.alertAmounts;
      } else {
        this.newItem.alertAmounts = 0;
      }
      // this.newItem.category = this.profileForm.value.category;
      // this.newItem.showAlert = this.profileForm.value.showAlert;

      this.newItem.storeArea = this.profileForm.value.storeArea;
      this.newItem.storeSecction = this.profileForm.value.storeSecction;
      this.newItem.storeType = this.profileForm.value.storeType;

      // this.newItem.uid = this.profileForm.value.uid;
      this.newItem.unit = this.profileForm.value.unit;

      // this.newItem.key = this.profileForm.value.key;
      this.newItem.statusCloud = 'S';

      if (this.checked) {
        this.newItem.type = 'Material';
      }

      if (this.checkedTool) {
        this.newItem.type = 'Tool';
      }
      this.newItem.itemActive = true;
      const fecha = new Date();
      let datasave = new DateSave();

      datasave.date = fecha.getDate();
      datasave.day = fecha.getDay();
      datasave.hours = fecha.getHours();
      datasave.minutes = fecha.getMinutes();
      datasave.month = fecha.getMonth();
      datasave.seconds = fecha.getSeconds();
      datasave.time = fecha.getTime();
      datasave.timezoneOffset = fecha.getTimezoneOffset();
      datasave.year = fecha.getFullYear();
      this.newItem.dateSave = datasave;


      if (this.haveKey) {
        this.coreservice.update(this.newItem);
      } else {
        // console.log(JSON.stringify(newUser));
        this.coreservice.save(this.newItem);
      }
    }
  }


  cleanExtencion(filename): any {
    const nombre = filename.split('.');
    return nombre[0].replace(/ /g, '_');
  }

  getFileExtension(filename): any {
    let ext = /^.+\.([^.]+)$/.exec(filename);
    return ext == null ? '' : ext[1];
  }

  onFileChange2(event): void {
    // let file = <File>event.target.files[0];
    // let name = this.cleanExtencion(file.name) + '.' + this.getFileExtension(file.name).toLowerCase();
    // let blob = file.slice(0, file.size, file.type);
    // let newFile = new File([blob], name, { type: file.type });
    // this.fileData = newFile;

    if (event.target.files && event.target.files.length > 0) {
      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.craftImagePreview = event.target.result;
        this.viewSafeResourceUrl = false;
        this.actionImagen(this.craftImagePreview);
      };
      const file = event.target.files[0];
      reader.readAsDataURL(file);
    }
  }

  actionImagen(valor: string): void {
    const arrayValor = valor.split(',');
    this.newItem.itemImageBase64String = arrayValor[1];
  }


  checkboxchTool(): void {
    if (!this.checkedTool) {
      this.checked = true;
    } else {
      this.checked = false;
    }
  }

  checkboxchMaterial(): void {
    if (!this.checked) {
      this.checkedTool = true;
    } else {
      this.checkedTool = false;
    }
  }

  showCategory(event): void {
    this.newItem.category = event.value;
    // console.log(event.value);
  }

  showAlert(): void {
    this.newItem.showAlert = this.profileForm.value.showAlert;
    // this.showAlertcheck = false;
    // console.log(this.showAlertcheck);
  }
}

