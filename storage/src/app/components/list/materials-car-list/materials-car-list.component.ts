import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Data } from 'src/app/model/data.model';

@Component({
  selector: 'app-materials-car-list',
  templateUrl: './materials-car-list.component.html',
  styleUrls: ['./materials-car-list.component.css']
})
export class MaterialsCarListComponent implements OnInit {

  @Input() item: Data;

  name = "";
  quantity = "";
  type = "";
  use = "";
  description = "";
  areaStorage = "";
  typeStorage = "";
  espaceStorage = "";
  ing: SafeResourceUrl;
  htmlT;
  constructor(private sanitizer: DomSanitizer, private router: Router,) { }

  ngOnInit(): void {
    this.setData();
  }


  setData() {
    this.name = this.item.itemName;
    this.quantity = this.item.itemNumber;
    this.type = this.item.itemType;
    this.use = this.item.itemUse;
    this.description = this.item.itemDescription;
    this.areaStorage = this.item.storeArea;
    this.typeStorage = this.item.storeType;
    this.espaceStorage = this.item.storeSecction;
    // console.log(this.item.itemImageString);

    

    if (this.item.itemImageString === undefined || this.item.itemImageString == null || this.item.itemImageString == '') {
      if(this.item.itemImageBase64String !== undefined && this.item.itemImageBase64String != null){
        this.ing = this.sanitizer.bypassSecurityTrustResourceUrl(`data:image/jpg;base64,${this.item.itemImageBase64String}`);
      }
    } else {
      let buffer = JSON.parse(this.item.itemImageString);
      let base64Img = this.arrayBufferToBase64(buffer.itemImage);
      this.ing = this.sanitizer.bypassSecurityTrustResourceUrl(`data:image/jpg;base64,${base64Img}`);
    }


  }

  arrayBufferToBase64(buffer) {
    var binary = '';
    var bytes = new Uint8Array(buffer);
    var len = bytes.byteLength;
    for (var i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    return window.btoa(binary);
  }

}
