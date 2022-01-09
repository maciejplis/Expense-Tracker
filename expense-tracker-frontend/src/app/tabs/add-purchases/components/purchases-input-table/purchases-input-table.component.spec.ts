import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchasesInputTableComponent } from './purchases-input-table.component';

describe('PurchasesInputTableComponent', () => {
  let component: PurchasesInputTableComponent;
  let fixture: ComponentFixture<PurchasesInputTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PurchasesInputTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PurchasesInputTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
