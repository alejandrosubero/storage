import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemEditNewComponent } from './item-edit-new.component';

describe('ItemEditNewComponent', () => {
  let component: ItemEditNewComponent;
  let fixture: ComponentFixture<ItemEditNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ItemEditNewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemEditNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
