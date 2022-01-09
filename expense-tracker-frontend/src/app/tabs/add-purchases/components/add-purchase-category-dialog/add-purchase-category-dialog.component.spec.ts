import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPurchaseShopDialogComponent } from './add-purchase-category-dialog.component';

describe('AddPurchaseShopDialogComponent', () => {
  let component: AddPurchaseShopDialogComponent;
  let fixture: ComponentFixture<AddPurchaseShopDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddPurchaseShopDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPurchaseShopDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
