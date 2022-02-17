import { ComponentFixture, TestBed } from '@angular/core/testing';

import {AddPurchaseCategoryDialog} from './add-purchase-category-dialog.component';

describe('AddPurchaseCategoryDialog', () => {
  let component: AddPurchaseCategoryDialog;
  let fixture: ComponentFixture<AddPurchaseCategoryDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddPurchaseCategoryDialog ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPurchaseCategoryDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
