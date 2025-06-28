import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Data } from 'src/app/model/data.model';
import { CoreService } from 'src/app/services/core.service';

@Component({
  selector: 'app-itens-list',
  templateUrl: './itens-list.component.html',
  styleUrls: ['./itens-list.component.css']
})
export class ItensListComponent implements OnInit {

  displayedColumns = ['itemName', 'itemNumber', 'itemUse', 'storeArea', 'storeSecction', 'aciones'];

  items: Data[] = [];
  dataSource: MatTableDataSource<Data> = new MatTableDataSource<Data>();

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  title = 'Store';


  constructor(
    private router: Router,
    private coreService: CoreService,
    private _liveAnnouncer: LiveAnnouncer,
    private titleService: Title,
    private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.coreService.emAllItems.subscribe(y => {
      this.items = y;
      // this.items.forEach(r => {
      //   console.log('items', r.type);
      // });
      this.dataSource.data = this.items;
    });

    this.titleService.setTitle("List items");
  }


  // ngAfterViewInit(): void {
  //   this.dataSource.paginator = this.paginator;
  //   this.dataSource.sort = this.sort;
  // }

  // ngOnChanges(changes: SimpleChanges): void {
  //   this.dataSource.paginator = this.paginator;
  //   this.dataSource.sort = this.sort;
  // }

  applyFilter(filterValue: string): void {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }


  addbut(): void {
    window.alert("addbutton");
  }

  editbut(): void {
    window.alert("editbutton");
  }


  onEditItem(row: Data): void {
    let jsonData = JSON.stringify(row);
    this.router.navigate(
      ['/items/menu/item-new-edit'],
      { queryParams: { id: jsonData } }
    );
  }

  // deveria llevar a un iten detalle 
  onItem(row: Data): void {
    let jsonData = JSON.stringify(row);
    this.router.navigate(
      ['/items/menu/item-new-edit'],
      { queryParams: { id: jsonData } }
    );
  }

  onDelete(row: Data): void {
    this.coreService.delete(row);
  }

}
