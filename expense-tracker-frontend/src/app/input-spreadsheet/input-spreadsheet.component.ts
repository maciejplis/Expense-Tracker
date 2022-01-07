import {Component, OnInit, ViewChild} from '@angular/core';
import Handsontable from "handsontable";
import {HotTableRegisterer} from "@handsontable/angular";

import {locale} from 'moment';
import {ExportFile} from "handsontable/plugins";
import { ShopsService } from 'build/expense-tracker-frontend-api';

const numbro = require('numbro')
const plPL = require('numbro/dist/languages/pl-PL.min')
numbro.registerLanguage(plPL)
numbro.setLanguage('pl-PL')

@Component({
  selector: 'app-input-spreadsheet',
  templateUrl: './input-spreadsheet.component.html',
  styleUrls: ['./input-spreadsheet.component.scss']
})
export class InputSpreadsheetComponent implements OnInit {

  private hotRegistry: HotTableRegisterer = new HotTableRegisterer()

  categories: string[] = [
    'Jedzenie',
    'Środki Czystości',
    'Środki do Higieny',
    '<p (click)="printData()">Dodaj nową kategorię</p>'
  ]

  products: string[] = [
    'Bułki',
    'Banany',
    'Gruszki',
    'Podpaski',
    'Cebula'
  ]

  hotSettings: Handsontable.GridSettings = {
    language: 'pl-PL',
    contextMenu: true,
    startRows: 10,
    stretchH: 'all',
    minSpareRows: 1,
    rowHeights: 35,
    columnHeaderHeight: 35,
    className: 'htMiddle',
    afterGetColHeader: function(col, TH) {
      TH.className = 'htMiddle'
    },
    columns: [
      {
        title: 'Kategoria',
        type: 'dropdown',
        source: this.categories,
        allowInvalid: false
      }, {
        title: 'Nazwa',
        type: 'autocomplete',
        source(query, process) {
          process([
            'Mięso mielone',
            'Wędlina',
            'Ryż'
          ])
        }
      }, {
        title: 'Ilość (szt / kg)',
        type: 'numeric'
      }, {
        title: 'Cena',
        type: 'numeric',
        numericFormat: {
          culture: 'pl-PL',
          pattern: '$ 0,0.00'
        }
      }, {
        title: 'Opis',
        type: 'text',
      }
    ],
    colHeaders: true,
    dropdownMenu: true,
    licenseKey: 'non-commercial-and-evaluation'
  }

  constructor(
    private readonly shopsService: ShopsService
  ) {
    locale('pl-PL')
    shopsService.getPurchaseShops().subscribe(response => {console.log("SHOPS: ", response)})
  }

  ngOnInit() {
  }

  printData(): void {
    const exportPlugin: ExportFile = this.hotRegistry.getInstance('spreadsheet').getPlugin('exportFile');
      exportPlugin.exportAsBlob('csv', {
        rowHeaders: true,
        columnHeaders: true,
        fileExtension: 'csv',
        filename: 'spreadsheet',
        mimeType: 'text/csv'
      }).text().then(text => console.log(text))
    }

}
