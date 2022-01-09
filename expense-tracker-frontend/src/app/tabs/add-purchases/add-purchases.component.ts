import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {
  PurchaseGroupDto,
  ShopDto,
  ShopsService,
  PurchasesService
} from 'build/expense-tracker-frontend-api';
import * as moment from 'moment';

import {locale} from 'moment';
import {CellProperties} from "handsontable/settings";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {AddPurchaseShopDialog} from "./components/add-purchase-shop-dialog/add-purchase-shop-dialog.component";
import {FormBuilder, FormControl} from "@angular/forms";

const numbro = require('numbro')
const plPL = require('numbro/dist/languages/pl-PL.min')
numbro.registerLanguage(plPL)
numbro.setLanguage('pl-PL')

@Component({
  selector: 'app-add-purchases',
  templateUrl: './add-purchases.component.html',
  styleUrls: ['./add-purchases.component.scss']
})
export class AddPurchasesComponent implements OnInit {

  @ViewChild("purchasesTable") purchasesTable?: any;
  @ViewChild("purchaseShop") purchaseShop?: any;
  @ViewChild("purchaseDate") purchaseDate?: any;

  shops: ShopDto[] = [];
  shopsControl: any;


  constructor(
    private shopsService: ShopsService,
    private purchasesService: PurchasesService,
    private dialog: MatDialog,
    private fb: FormBuilder
  ) {
    locale('pl-PL')
  }

  ngOnInit() {
    this.shopsService.getPurchaseShops()
      .subscribe(resp => this.shops = resp)

    this.shopsControl = this.fb.control({})
  }

  onPurchasesSave() {
    const hot = this.purchasesTable.hotRegistry.getInstance("purchases-spreadsheet");
    let purchasesData = hot.getCellsMeta()
        .filter((val: CellProperties, i: number) => i % 5 == 0)
        .map((c: any) => c.value)
        .map((c: any, i: number) => {
          return [c, ...hot.getDataAtRow(i)]
        })

    const purchaseGroup: PurchaseGroupDto = {
      shop: this.purchaseShop.value,
      date: moment(this.purchaseDate.nativeElement.value).format("YYYY-MM-DD"),
      purchases: purchasesData.filter((row: any) => !row.every((cell: any) => cell == null))
        .map((row: any) => {
          console.log(row)
          return {
            id: '',
            category: row[0],
            name: row[2],
            amount: row[3],
            price: row[4],
            description: row[5]
          }
        })
    }

    this.purchasesService.addPurchaseGroup(purchaseGroup).subscribe();
  }

  addShop(): void {
    const dialogRef = this.dialog.open(AddPurchaseShopDialog, {
      data: {name: ""},
    });

    dialogRef.afterClosed().subscribe(result => {
      this.shopsService.addPurchaseShop({
        id: "",
        name: result
      }).subscribe(response => {
        this.shopsControl?.patchValue(response)
        this.shops = [...this.shops, response];
      })
    });
  }
}

