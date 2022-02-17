import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPurchaseShopDialog } from './add-purchase-shop-dialog.component';

describe('AddPurchaseShopDialog', () => {
  let component: AddPurchaseShopDialog;
  let fixture: ComponentFixture<AddPurchaseShopDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddPurchaseShopDialog ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPurchaseShopDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
