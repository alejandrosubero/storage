import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

import { Router } from '@angular/router';
import { Data } from '../model/data.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AESEncryptDecryptService } from './aesencrypt-decrypt-service.service';
import { FiverbaseConextionService } from './fiverbase-conextion.service';
import { SpinnerOverlayService } from './spinner-overlay.service';
import { environment } from 'src/environments/environment';

// import * as _ from "lodash"; 

@Injectable({
  providedIn: 'root'
})
export class CoreService {


  urlBase = environment.urlBase;
  urlStorageBase = environment.urlStorageBase;
  urlBaseDeleteStorage = environment.urlBaseDeleteStorage;
  baseStorageRef = environment.baseStorageRef;


  itemsList: Data[] = [];

  private list = new BehaviorSubject<Array<Data>>(new Array<Data>());
  emAllItems = this.list.asObservable();

  private tools = new BehaviorSubject<Array<Data>>(new Array<Data>());
  emTools = this.tools.asObservable();

  private materials = new BehaviorSubject<Array<Data>>(new Array<Data>());
  emMaterials = this.materials.asObservable();

  constructor(
    private router: Router,
    public snackBar: MatSnackBar,
    protected http: HttpClient,
    private encryptService: AESEncryptDecryptService,
    private fire: FiverbaseConextionService,
    // private spinnerService: SpinnerOverlayService,
    ) { }


  updateTools(newListTablet: Data[]): void {
    this.tools.next(newListTablet);
  }


  updateMaerials(newListTablet: Data[]): void {
    this.materials.next(newListTablet);
  }


  updateItemlist(newListTablet: Data[]): void {
    this.list.next(newListTablet);
  }

  updateList(list: Data[]): void {
    const materialList = list.filter((y: Data) => y.type == 'Material');
    this.updateMaerials(materialList);
    const toolsList = list.filter((y: Data) => y.type == 'Tool');
    this.updateTools(toolsList);
   
  }


  getAll(): void {
    // this.spinnerService.spinnerActive = false;
    // this.spinnerService.show();
    const url = `${this.urlStorageBase}${this.baseStorageRef}`;
    this.fire.getAll(url).subscribe((res) => {
      if (res) {
        const processedOrders: Data[] = [];

        for (const [key, value] of Object.entries(res)) {
          const valset = value as Data;
          const dataValset = this.dencryData(valset);
          valset.key = key;
          processedOrders.push(dataValset);
          
        }
        this.itemsList = processedOrders;
        this.updateList(processedOrders);
        this.updateItemlist(processedOrders);
        // this.spinnerService.hide();
      }
    }, (error: any) => {
      // this.spinnerService.hide();
      // this.snackBar.open('FAILL THE RESPONSE OFF SERVER', 'Cerrar');
    });
  }


  get(res: any): Data {
    if (res) {
      const processedOrders: Data[] = [];
      for (const [key, value] of Object.entries(res)) {
        const valset = value as Data;
        const dataValset = this.dencryData(valset);
        valset.key = key;
        processedOrders.push(this.setData(dataValset));
      }
      
      return processedOrders[0];
      
    }
  }

  navigateByUrl(ruta: string): void {
    this.router.navigateByUrl(ruta);
  }

  // tslint:disable-next-line: typedef
  findMaterials(id: any) {
    let lista: Array<Data> = new Array<Data>();
    this.emMaterials.subscribe(list => {
      lista = list.filter(x => x.key == id);
    });
    return lista;
  }

  findTools(id: any) {
    let lista: Array<Data> = new Array<Data>();
    this.emTools.subscribe(list => {
      lista = list.filter(x => x.key == id);
    });
    return lista;
  }


  save(body: any): void {
    // this.spinnerService.spinnerActive = false;
    // this.spinnerService.show();
    const url = `${this.urlStorageBase}${this.baseStorageRef}`;
    const dataValset = this.encryData(body);
    this.fire.save(dataValset, url).subscribe(postResponse => {
      this.router.navigateByUrl('/items/menu/list/detail');
      // this.spinnerService.hide();
    });
  }


  delete(body: any): void {
    // this.spinnerService.spinnerActive = false;
    // this.spinnerService.show();
    const url = `${this.urlBaseDeleteStorage}`;
    this.fire.delete(body, url).subscribe(postResponse => {
      this.router.navigateByUrl('/items/menu/list/detail');
      // this.spinnerService.hide();
    });
  }



  update(item: Data): void {
    // this.spinnerService.spinnerActive = false;
    // this.spinnerService.show();
    const url = `${this.urlStorageBase}${this.baseStorageRef}`;
    const dataValset = this.encryData(item);
    this.fire.update(dataValset, url).subscribe(res => {
      console.log(res);
      this.router.navigateByUrl('/items/menu/list/detail');
      // this.spinnerService.hide();
    });

  }

  encrypt(data): string {
    return this.encryptService.encripted(data);
  }

  decrypt(dataEcrypted): string {
    return this.encryptService.decrypted(dataEcrypted);
  }

  // private show(): void {
  //   this.spinnerService.spinnerActive = false;// cuando se usa un interceptor se una este parametro para controlar el spinner 
  //   this.spinnerService.show();
  // }

  // private hide(): void {
  //   this.spinnerService.hide();
  //   this.spinnerService.spinnerActive = true;
  // }

  setData(d: Data): Data {

    let item = new Data();
    if (d.dateSave != null && d.dateSave !== undefined) {
      item.dateSave = d.dateSave;
    }
    if (d.itemActive != null && d.itemActive !== undefined) {
      item.itemActive = d.itemActive;
    }
    if (d.category != null && d.category !== undefined) {
      item.category = d.category;
    }

    if (d.itemDescription != null && d.itemDescription !== undefined) {
      item.itemDescription = d.itemDescription;
    }

    if (d.itemName != null && d.itemName !== undefined) {
      item.itemName = d.itemName;
    }
    if (d.itemNumber != null && d.itemNumber !== undefined) {
      item.itemNumber = d.itemNumber;
    }
    if (d.itemType != null && d.itemType !== undefined) {
      item.itemType = d.itemType;
    }
    if (d.itemUse != null && d.itemUse !== undefined) {
      item.itemUse = d.itemUse;
    }

    if (d.showAlert != null && d.showAlert !== undefined) {
      item.showAlert = d.showAlert;
    }

    if (d.statusCloud != null && d.statusCloud !== undefined) {
      item.statusCloud = d.statusCloud;
    }

    if (d.storeArea != null && d.storeArea !== undefined) {
      item.storeArea = d.storeArea;
    }
    if (d.storeSecction != null && d.storeSecction !== undefined) {
      item.storeSecction = d.storeSecction;
    }
    if (d.storeType != null && d.storeType !== undefined) {
      item.storeType = d.storeType;
    }

    if (d.type != null && d.type !== undefined) {
      item.type = d.type;
    }
    if (d.uid != null && d.uid !== undefined) {
      item.uid = d.uid;
    }
    if (d.unit != null && d.unit !== undefined) {
      item.unit = d.unit;
    }
    if (d.key != null && d.key !== undefined) {
      item.key = d.key;
    }
    if (d.itemImageString != null && d.itemImageString !== undefined) {
      item.itemImageString = d.itemImageString;
    }
    if (d.itemImage != null && d.itemImage !== undefined) {
      item.itemImage = d.itemImage;
    }
    return item;
  }

  encryData(d: Data): Data {

    let item = new Data();
    if (d.dateSave != null && d.dateSave !== undefined) {
      item.dateSave = d.dateSave;
    }

    if (d.itemActive != null && d.itemActive !== undefined) {
      item.itemActive = d.itemActive;
    }
    if (d.category != null && d.category !== undefined) {
      item.category = this.encryptService.encripted(d.category);
    }

    if (d.itemDescription != null && d.itemDescription !== undefined) {
      item.itemDescription =  this.encryptService.encripted(d.itemDescription);
    }

    if (d.itemName != null && d.itemName !== undefined) {
      item.itemName =  this.encryptService.encripted(d.itemName);
    }
    if (d.itemNumber != null && d.itemNumber !== undefined) {
      item.itemNumber =  this.encryptService.encripted(d.itemNumber);
    }
    if (d.itemType != null && d.itemType !== undefined) {
      item.itemType =  this.encryptService.encripted(d.itemType);
    }
    if (d.itemUse != null && d.itemUse !== undefined) {
      item.itemUse =  this.encryptService.encripted(d.itemUse);
    }

    if (d.showAlert != null && d.showAlert !== undefined) {
      item.showAlert = d.showAlert;
    }

    if (d.statusCloud != null && d.statusCloud !== undefined) {
      item.statusCloud =  this.encryptService.encripted(d.statusCloud);
    }

    if (d.storeArea != null && d.storeArea !== undefined) {
      item.storeArea =  this.encryptService.encripted(d.storeArea);
    }
    if (d.storeSecction != null && d.storeSecction !== undefined) {
      item.storeSecction =  this.encryptService.encripted(d.storeSecction);
    }
    if (d.storeType != null && d.storeType !== undefined) {
      item.storeType =  this.encryptService.encripted(d.storeType);
    }

    if (d.type != null && d.type !== undefined) {
      item.type =this.encryptService.encripted(d.type);
    }

    if (d.uid != null && d.uid !== undefined) {
      item.uid = d.uid;
    }
    if (d.unit != null && d.unit !== undefined) {
      item.unit =  this.encryptService.encripted(d.unit);
    }
    if (d.key != null && d.key !== undefined) {
      item.key =  d.key;
    }
    if (d.itemImageString != null && d.itemImageString !== undefined) {
      item.itemImageString =  this.encryptService.encripted(d.itemImageString);
    }
    if (d.itemImageBase64String != null && d.itemImageBase64String !== undefined) {
      item.itemImageBase64String =  this.encryptService.encripted(d.itemImageBase64String);
    }
    if (d.itemImage != null && d.itemImage !== undefined) {
      item.itemImage = d.itemImage;
    }
    return item;
  }


  dencryData(d: Data): Data {

    let item = new Data();
    if (d.dateSave != null && d.dateSave !== undefined) {
      item.dateSave = d.dateSave;
    }

    if (d.itemActive != null && d.itemActive !== undefined) {
      item.itemActive = d.itemActive;
    }
    if (d.category != null && d.category !== undefined) {
      item.category = this.encryptService.decrypted(d.category);
    }

    if (d.itemDescription != null && d.itemDescription !== undefined) {
      item.itemDescription =  this.encryptService.decrypted(d.itemDescription);
    }

    if (d.itemName != null && d.itemName !== undefined) {
      item.itemName =  this.encryptService.decrypted(d.itemName);
    }
    if (d.itemNumber != null && d.itemNumber !== undefined) {
      item.itemNumber =  this.encryptService.decrypted(d.itemNumber);
    }
    if (d.itemType != null && d.itemType !== undefined) {
      item.itemType =  this.encryptService.decrypted(d.itemType);
    }
    if (d.itemUse != null && d.itemUse !== undefined) {
      item.itemUse =  this.encryptService.decrypted(d.itemUse);
    }

    if (d.showAlert != null && d.showAlert !== undefined) {
      item.showAlert = d.showAlert;
    }

    if (d.statusCloud != null && d.statusCloud !== undefined) {
      item.statusCloud =  this.encryptService.decrypted(d.statusCloud);
    }

    if (d.storeArea != null && d.storeArea !== undefined) {
      item.storeArea =  this.encryptService.decrypted(d.storeArea);
    }
    if (d.storeSecction != null && d.storeSecction !== undefined) {
      item.storeSecction =  this.encryptService.decrypted(d.storeSecction);
    }
    if (d.storeType != null && d.storeType !== undefined) {
      item.storeType =  this.encryptService.decrypted(d.storeType);
    }

    if (d.type != null && d.type !== undefined) {
      item.type = this.encryptService.decrypted(d.type); 
    }

    if (d.uid != null && d.uid !== undefined) {
      item.uid = d.uid;
    }
    if (d.unit != null && d.unit !== undefined) {
      item.unit =  this.encryptService.decrypted(d.unit);
    }
    if (d.key != null && d.key !== undefined) {
      item.key =  d.key;
    }

    if (d.itemImageString != null && d.itemImageString !== undefined) {
      item.itemImageString =  this.encryptService.decrypted(d.itemImageString);
    }
    if (d.itemImageBase64String != null && d.itemImageBase64String !== undefined) {
      item.itemImageBase64String =  this.encryptService.decrypted(d.itemImageBase64String);
    }

    if (d.itemImage != null && d.itemImage !== undefined) {
      item.itemImage = d.itemImage;
    }
    return item;
  }

}

