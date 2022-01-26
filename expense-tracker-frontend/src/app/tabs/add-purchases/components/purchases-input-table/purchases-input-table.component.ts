import {AfterViewInit, Component, forwardRef, HostBinding, HostListener, OnInit} from '@angular/core';
import {HotTableRegisterer} from "@handsontable/angular";
import {CategoriesService, CategoryDto, PurchasesService} from 'build/expense-tracker-frontend-api';
import Handsontable from "handsontable";
import {MatDialog} from "@angular/material/dialog";
import {AddPurchaseCategoryDialog} from "../add-purchase-category-dialog/add-purchase-category-dialog.component";
import {filter, Observable} from "rxjs";
import {isNonNull} from "../../../../common/utils";
import {CellChange, RowObject} from "handsontable/common";
import {tap} from "rxjs/operators";
import {
  AbstractControl,
  ControlValueAccessor,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
  ValidationErrors,
  Validator
} from "@angular/forms";

enum Columns {
  CATEGORY = "category",
  NAME = "name",
  AMOUNT = "amount",
  PRICE = "price",
  DESCRIPTION = "description",
}

export const PURCHASES_HOT_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => PurchasesInputTableComponent),
  multi: true
}

export const PURCHASES_HOT_VALUE_VALIDATOR: any = {
  provide: NG_VALIDATORS,
  useExisting: forwardRef(() => PurchasesInputTableComponent),
  multi: true
}

@Component({
  selector: 'app-purchases-input-table',
  templateUrl: './purchases-input-table.component.html',
  styleUrls: ['./purchases-input-table.component.scss'],
  providers: [PURCHASES_HOT_VALUE_ACCESSOR, PURCHASES_HOT_VALUE_VALIDATOR],
  host: {
    '(blur)': '_onTouch()'
  }
})
export class PurchasesInputTableComponent implements OnInit, AfterViewInit, ControlValueAccessor, Validator {

  private REQUIRED_COLUMNS: Columns[] = [Columns.CATEGORY, Columns.NAME, Columns.AMOUNT, Columns.PRICE];
  private INITIAL_ROWS: number = 10
  private ADD_CATEGORY_OPTION: string = "Add Category"

  private hot!: Handsontable;

  public _onChange!: Function;
  public _onTouch!: Function;

  purchases: RowObject[] = Array.from({length: this.INITIAL_ROWS}, () => ({}))
  categories: CategoryDto[] = []
  hotSettings: Handsontable.GridSettings = {
    data: this.purchases,
    language: 'pl-PL',
    contextMenu: true,
    stretchH: 'all',
    minSpareRows: 1,
    rowHeights: 35,
    columnHeaderHeight: 35,
    className: 'htMiddle',
    colHeaders: true,
    licenseKey: 'non-commercial-and-evaluation',
    beforeChange: changes => this.handleCellChange(changes),
    afterChange: changes => changes && this.afterChange(),
    afterRemoveRow: () => this.afterChange(),
    afterGetColHeader: (col, headerElement) => {
      headerElement.className = 'htMiddle'
    },
    beforeKeyDown: (event: any) => {
      if (!event.target.closest(".handsontableInput")) {
        event.stopImmediatePropagation();
      }
    },
    columns: [
      {
        data: Columns.CATEGORY,
        title: 'Kategoria',
        type: 'dropdown',
        allowInvalid: false,
        source: (query: string, process: Function) => this.getAvailableCategories(query, process)
      }, {
        data: Columns.NAME,
        title: 'Nazwa',
        type: 'autocomplete',
        source: (query: string, process: Function) => this.getPurchaseNameHints(query, process)
      }, {
        data: Columns.AMOUNT,
        title: 'Ilość (szt / kg)',
        type: 'numeric'
      }, {
        data: Columns.PRICE,
        title: 'Cena',
        type: 'numeric',
        numericFormat: {
          culture: 'pl-PL',
          pattern: '$ 0,0.00'
        }
      }, {
        data: Columns.DESCRIPTION,
        title: 'Opis',
        type: 'text',
      }
    ]
  }

  constructor(
    private categoriesService: CategoriesService,
    private purchasesService: PurchasesService,
    private dialog: MatDialog
  ) {
  }

  @HostBinding('tabindex') tabindex = 0;
  @HostListener('focus')
  focusHandler() {
    this.hot.selectCell(0, 0);
    this.hot.getActiveEditor()?.beginEditing();
  }

  ngOnInit(): void {
    this.categoriesService.getPurchaseCategories()
      .subscribe((categories: CategoryDto[]) => this.updateAvailableCategories(...categories));
  }

  ngAfterViewInit(): void {
    this.hot = new HotTableRegisterer().getInstance("purchases-spreadsheet");
  }

  writeValue(value: (string | number)[][]): void {
    // Ignore
  }

  registerOnChange(fn: Function): void {
    this._onChange = fn;
  }

  registerOnTouched(fn: Function): void {
    this._onTouch = fn;
  }

  afterChange(): void {
    this._onChange(this.purchases);
    this._onTouch();
  }

  validate({value}: AbstractControl): ValidationErrors | null {
    const purchases = this.getFilteredPurchases();

    if (purchases.length === 0) {
      return {notEmpty: true};
    }

    const allRowsAreFilled = purchases.every((row: RowObject) => {
      const keys = Object.keys(row);
      return this.REQUIRED_COLUMNS.every(col => keys.includes(col) && row[col] != null);
    });

    if (!allRowsAreFilled) {
      return {unfilled: true}
    }

    return null;
  }

  getFilteredPurchases(): RowObject[] {
    return this.purchases.filter((row: RowObject) => {
      const keys = Object.keys(row);
      return keys.length > 0 && keys.some(key => row[key] != null);
    });
  }

  getAvailableCategories(query: string, callback: Function): void {
    callback([
      ...this.categories
        .map(c => c.name)
        .filter(cName => cName.includes(query)),
      this.ADD_CATEGORY_OPTION
    ]);
  }

  getPurchaseNameHints(query: string, callback: Function): void {
    this.purchasesService.queryPurchaseNames(query).subscribe(response => callback(response));
  }

  handleCellChange(changes: CellChange[] | null): boolean {
    if (changes != null) {
      const addCategoryChanges: CellChange[] = changes
        .filter(c => c[1] === Columns.CATEGORY)
        .map(c => this.addCategoryMetaToCell(c))
        .filter(c => c[3] === this.ADD_CATEGORY_OPTION)
        .map(c => this.setCellValue(c, null));

      if (addCategoryChanges.length > 0) {
        this.openAddCategoryDialog().subscribe(newCategory => {
          this.hot.setDataAtRowProp(
            addCategoryChanges.map(c => [c[0], c[1], newCategory.name])
          );
        })
      }
    }

    return true;
  }

  addCategoryMetaToCell(cellChange: CellChange): CellChange {
    this.hot.setCellMeta(cellChange[0], 0, "value", this.categories.find(c => c.name == cellChange[3]))
    return cellChange;
  }

  setCellValue(cellChange: CellChange, value: any): CellChange {
    cellChange[3] = value;
    return cellChange;
  }

  openAddCategoryDialog(): Observable<CategoryDto> {
    return this.dialog
      .open(AddPurchaseCategoryDialog, {data: {name: ""}})
      .afterClosed()
      .pipe(
        filter(isNonNull),
        tap((savedCategory: CategoryDto) => this.updateAvailableCategories(savedCategory))
      );
  }

  updateAvailableCategories(...categories: CategoryDto[]): void {
    this.categories = this.categories
      .concat(categories)
      .sort((a, b) => a.name.localeCompare(b.name));
  }
}
