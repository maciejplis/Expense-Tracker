import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchasesConfirmationDialog } from './purchases-confirmation-dialog.component';

describe('PurchasesConfirmationDialogComponent', () => {
  let component: PurchasesConfirmationDialog;
  let fixture: ComponentFixture<PurchasesConfirmationDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PurchasesConfirmationDialog ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PurchasesConfirmationDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
