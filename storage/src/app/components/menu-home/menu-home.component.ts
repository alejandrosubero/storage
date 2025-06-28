import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { TooltipPosition } from '@angular/material/tooltip';
import { Subject } from 'rxjs';
import { LogingResponse } from 'src/app/model/loging-response.model';
import { CoreService } from 'src/app/services/core.service';

@Component({
  selector: 'app-menu-home',
  templateUrl: './menu-home.component.html',
  styleUrls: ['./menu-home.component.css']
})
export class MenuHomeComponent implements OnInit {


  numberAlerts: number = 0;
  colorAlert = 'primary';
  positionOptions: TooltipPosition[] = ['below', 'above', 'left', 'right'];
  position = new FormControl(this.positionOptions[0]);
  private present$ = new Subject<void>();
  seccion: LogingResponse;

  public iscollapse = true;
  public iscollapse2 = true;
  photo;
  search = '';
  imgsrc: any;
  public profileImagin = '../../../assets/images/user-profile1.png';

  searchInput = '';
  currentRoute: string;
  routeMenu = '/jshandy-man-services/menu';

  constructor(private core: CoreService) { }

  ngOnInit(): void {

    this.core.getAll();
  }



  buscar(text:any){}

  changeColase(){}
  

}
