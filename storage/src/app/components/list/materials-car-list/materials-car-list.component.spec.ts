import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaterialsCarListComponent } from './materials-car-list.component';

describe('MaterialsCarListComponent', () => {
  let component: MaterialsCarListComponent;
  let fixture: ComponentFixture<MaterialsCarListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MaterialsCarListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MaterialsCarListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
