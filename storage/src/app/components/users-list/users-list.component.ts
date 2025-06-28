import { LiveAnnouncer } from '@angular/cdk/a11y';
import { AfterViewInit, Component, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NewUser } from 'src/app/model/newUser.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit, AfterViewInit, OnChanges {

  // ng g c components/users-list --module=components\componet.module.ts

  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  displayedColumns: string[] = ['userFirsName', 'mail', 'aciones'];
  dataSource2: MatTableDataSource<NewUser> = new MatTableDataSource<NewUser>();

  dataList2 = new Array<NewUser>(); //dataListEstimate2

  title = 'Users List';

  constructor(
    private router: Router,
    private _snackBar: MatSnackBar,
    private userService: UserService,
    private _liveAnnouncer: LiveAnnouncer,) {
    this.onGetData2();
  }

  ngOnInit(): void {
    this.userService.em2.subscribe(y => {
      this.dataList2 = y;
      this.dataSource2.data = this.dataList2;
    });
  }

  onGetData2(): void {
    this.userService.getAllUsers();
  }

  onEditUser(row: NewUser): void {
    let user = JSON.stringify(row);

    this.router.navigate(
      ['/items/menu/newuser'],
      { queryParams: { id: user } }
    );
  }

  ngAfterViewInit(): void {
    this.dataSource2.paginator = this.paginator;
    this.dataSource2.sort = this.sort;
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.dataSource2.paginator = this.paginator;
    this.dataSource2.sort = this.sort;
  }


  onCreateNewUser(): void {
    this.router.navigateByUrl('/items/menu/newuser');
  }


  onDeleteUser(row: NewUser) {
    this.userService.deleteUser(row);
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource2.filter = filterValue.trim().toLowerCase();
  }


  /** Announce the change in sort state for assistive technology. */
  announceSortChange(sortState: Sort) {
    // This example uses English messages. If your application supports
    // multiple language, you would internationalize these strings.
    // Furthermore, you can customize the message to add additional
    // details about the values being sorted.
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }




}
