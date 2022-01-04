import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputSpreadsheetComponent } from './input-spreadsheet.component';

describe('InputSpreadsheetComponent', () => {
  let component: InputSpreadsheetComponent;
  let fixture: ComponentFixture<InputSpreadsheetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InputSpreadsheetComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InputSpreadsheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
