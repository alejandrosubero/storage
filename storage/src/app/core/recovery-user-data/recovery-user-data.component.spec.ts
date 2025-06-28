import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecoveryUserDataComponent } from './recovery-user-data.component';

describe('RecoveryUserDataComponent', () => {
  let component: RecoveryUserDataComponent;
  let fixture: ComponentFixture<RecoveryUserDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecoveryUserDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecoveryUserDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
