import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItensListComponent } from './itens-list.component';

describe('ItensListComponent', () => {
  let component: ItensListComponent;
  let fixture: ComponentFixture<ItensListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ItensListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ItensListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
