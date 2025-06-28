import { LiveAnnouncer } from '@angular/cdk/a11y';
import { AfterViewInit, Component, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CoreService } from 'src/app/services/core.service';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { Data } from 'src/app/model/data.model';

import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';




@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  // ng g c components/users-list --module=components\componet.module.ts
  // ng g c components/materials-list --module=components\list\list.module.ts
  tableToolView = false;
  tableView = false;
  toolsView = false;
  materialsView = false;
  homeView = true;
  homeTabletView = false;


  title = 'Store';
  materiales: Data[] = [];
  toolsList: Data[] = [];
  allItems: Data[] = [];
  constructor(
    private router: Router,
    private _snackBar: MatSnackBar,
    private coreService: CoreService,
    private _liveAnnouncer: LiveAnnouncer,
    private titleService: Title) {
    // this.clearActionView();
  }

  ngOnInit(): void {
    this.titleService.setTitle('Store');
    this.coreService.emMaterials.subscribe(materials => {
      this.materiales = materials;
    });

    this.coreService.emTools.subscribe(tools => {
      this.toolsList = tools;
    });

    this.coreService.emAllItems.subscribe(allI => {
      this.allItems = allI;
    });
    this.clearActionView();
  }

  toolsActionView(): void {
    this.homeView = false;
    this.materialsView = false;
    this.toolsView = true;
  }


  materialsActionView(): void {
    this.homeView = false;
    this.toolsView = false;
    this.materialsView = true;
  }

  clearActionView(): void {
    this.coreService.getAll();
    this.homeView = true;
    this.toolsView = false;
    this.materialsView = false;
  }

  changeView(): void {
    if (this.tableView) {
      this.tableView = false;
    } else {
      this.tableView = true;
    }
  }


  changeViewTool(): void {
    if (this.tableToolView) {
      this.tableToolView = false;
    } else {
      this.tableToolView = true;
    }
  }


  changeViewHome(): void{
    // homeTabletView = false;
    if (this.homeTabletView) {
      this.homeTabletView = false;
    } else {
      this.homeTabletView = true;
    }
  }

  onEditItem(row: Data): void {
    let jsonData = JSON.stringify(row);
    this.router.navigate(
      ['/items/menu/item-new-edit'],
      { queryParams: { id: jsonData } }
    );
  }


  onCreate(): void {
    this.router.navigateByUrl('/items/menu/item-new-edit');
  }

  onActionView(row: Data): void {
    if (row.type === 'Material') {
      this.materialsActionView();
    } else {
      this.toolsActionView();
    }

  }

}









