import {Component, OnInit} from '@angular/core';
import {HotTableRegisterer} from "@handsontable/angular";
import {CategoriesService, CategoryDto, PurchasesService} from 'build/expense-tracker-frontend-api';
import Handsontable from "handsontable";
import {MatDialog} from "@angular/material/dialog";
import {AddPurchaseCategoryDialog} from "../add-purchase-category-dialog/add-purchase-category-dialog.component";

@Component({
  selector: 'app-purchases-input-table',
  templateUrl: './purchases-input-table.component.html',
  styleUrls: ['./purchases-input-table.component.scss']
})
export class PurchasesInputTableComponent implements OnInit {

  private hotRegistry: HotTableRegisterer = new HotTableRegisterer();

  categories: CategoryDto[] = []
  hotSettings: Handsontable.GridSettings = {
    language: 'pl-PL',
    contextMenu: true,
    startRows: 10,
    stretchH: 'all',
    minSpareRows: 1,
    rowHeights: 35,
    columnHeaderHeight: 35,
    className: 'htMiddle',
    colHeaders: true,
    licenseKey: 'non-commercial-and-evaluation',
    beforeChange: changes => this.onCellChange(changes),
    afterGetColHeader: function(col, TH) {
      TH.className = 'htMiddle'
    },
    columns: [
      {
        data: 'category',
        title: 'Kategoria',
        type: 'dropdown',
        allowInvalid: false,
        source: (query, process) => process(this.categories.map(c => c.name))

      }, {
        data: 'name',
        title: 'Nazwa',
        type: 'autocomplete',
        source: (query, process) => {
          this.purchasesService.queryPurchaseNames(query)
            .subscribe(response => process(response))
        }
      }, {
        data: 'amount',
        title: 'Ilość (szt / kg)',
        type: 'numeric'
      }, {
        data: 'price',
        title: 'Cena',
        type: 'numeric',
        numericFormat: {
          culture: 'pl-PL',
          pattern: '$ 0,0.00'
        }
      }, {
        data: 'description',
        title: 'Opis',
        type: 'text',
      }
    ]
  }

  constructor(
    private categoriesService: CategoriesService,
    private purchasesService: PurchasesService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.categoriesService.getPurchaseCategories()
      .subscribe(resp => this.categories = [...resp, {id: "", name: "Add Category"}]);
  }

  onCellChange(changes: Handsontable.CellChange[] | null): boolean {
    if (changes !== null) {
      let addCategory= false;
      const hot = this.hotRegistry.getInstance("purchases-spreadsheet");
      changes.filter(c => c[1] === "category")
        .forEach(c => {
          hot.setCellMeta(c[0], 0, "value", this.categories.find(cat => cat.name == c[3]));
          if(c[3] == "Add Category") {
            c[3] = null;
            addCategory = true;
          }
          return c;
        })

      if (addCategory) {
        this.addCategory()
      }
    }
    return true;
  }

  addCategory(): void {
    const dialogRef = this.dialog.open(AddPurchaseCategoryDialog, {
      data: {name: ""},
    });

    dialogRef.afterClosed().subscribe(result => {
      this.categoriesService.addPurchaseCategory({
        id: "",
        name: result
      }).subscribe(response => {
        this.categories.splice(this.categories.length-1, 0, response);
      })
    });
  }
}
