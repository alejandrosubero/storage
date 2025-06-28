import { LiveAnnouncer } from '@angular/cdk/a11y';
import { AfterViewInit, Component, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CoreService } from 'src/app/services/core.service';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Title } from '@angular/platform-browser';
import { Data } from 'src/app/model/data.model';


@Component({
  selector: 'app-materials-list',
  templateUrl: './materials-list.component.html',
  styleUrls: ['./materials-list.component.css']
})
export class MaterialsListComponent implements OnInit, AfterViewInit, OnChanges {

  displayedColumns = ['itemName', 'itemNumber', 'itemUse', 'storeArea', 'storeSecction', 'storeType', 'aciones'];
  // displayedColumns = ['itemName', 'itemNumber', 'itemUse', , 'itemType','storeArea', 'storeSecction','storeType'];

  materiales: Data[] = [];
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
    this.coreService.emMaterials.subscribe(y => {
      // console.log('Materials: ', y);
      this.materiales = y;
      this.dataSource.data = this.materiales;
    });


    this.titleService.setTitle("Lista de items");
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(filterValue: string) : void {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  // applyFilter(event: Event): void {
  //   const filterValue = (event.target as HTMLInputElement).value;
  //   this.dataSource.filter = filterValue.trim().toLowerCase();
  // }


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


  onCreateNewUser(): void {
    this.router.navigateByUrl('/items/menu/newuser');
  }


  onDelete(row: Data): void {
    this.coreService.delete(row);
  }

}








