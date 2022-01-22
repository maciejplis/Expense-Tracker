import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {PurchaseGroupDto, PurchasesService, ShopDto, ShopsService} from 'build/expense-tracker-frontend-api';
import * as moment from 'moment';
import {locale} from 'moment';
import {CellProperties} from "handsontable/settings";
import {MatDialog} from "@angular/material/dialog";
import {AddPurchaseShopDialog} from "./components/add-purchase-shop-dialog/add-purchase-shop-dialog.component";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {filter} from "rxjs";
import {isNonNull} from "../../common/utils";
import Handsontable from "handsontable";
import {HotTableRegisterer} from "@handsontable/angular";
import {
  PurchasesConfirmationDialog
} from "./components/purchases-confirmation-dialog/purchases-confirmation-dialog.component";
import {MatSnackBar} from "@angular/material/snack-bar";

const numbro = require('numbro')
const plPL = require('numbro/dist/languages/pl-PL.min')
numbro.registerLanguage(plPL)
numbro.setLanguage('pl-PL')
locale('pl-PL')

@Component({
  selector: 'app-add-purchases',
  templateUrl: './add-purchases.component.html',
  styleUrls: ['./add-purchases.component.scss']
})
export class AddPurchasesComponent implements OnInit, AfterViewInit {

  @ViewChild("purchasesTable") purchasesTable?: any;
  @ViewChild("purchaseShop") purchaseShop?: any;
  @ViewChild("purchaseDate") purchaseDate?: any;

  purchasesForm: FormGroup;
  shops: ShopDto[] = [];

  hot!: Handsontable;

  constructor(
    private shopsService: ShopsService,
    private purchasesService: PurchasesService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    fb: FormBuilder
  ) {
    this.purchasesForm = fb.group({
      shop: [null, Validators.required],
      date: [new Date(), Validators.required],
      purchases: [null]
    })
  }

  ngOnInit() {
    this.shopsService.getPurchaseShops()
      .subscribe((shops: ShopDto[]) => this.updateAvailableShops(...shops));
  }

  ngAfterViewInit(): void {
    this.hot = new HotTableRegisterer().getInstance("purchases-spreadsheet");
  }

  onPurchasesSave(): void {
    this.purchasesForm.controls['purchases'].markAsTouched()

    if(this.purchasesForm.invalid) {
      return;
    }

    let purchasesData = this.hot.getCellsMeta()
      .filter((val: CellProperties, i: number) => i % 5 == 0) // get every 5th cell i.e. first column
      .map((c: CellProperties) => c['value'])
      .map((c: string, i: number) => [c, ...this.hot.getDataAtRow(i).slice(1)]) // join categories meta with other columns;

    const purchaseGroup: PurchaseGroupDto = {
      shop: this.purchaseShop.value,
      date: moment(this.purchaseDate.nativeElement.value).format("YYYY-MM-DD"),
      purchases: purchasesData.filter((row: any) => !row.every((cell: any) => cell == null))
        .map((row: any) => {
          return {
            id: '',
            category: row[0],
            name: row[1],
            amount: row[2],
            price: row[3],
            description: row[4]
          }
        })
    }

    const total = purchaseGroup.purchases.map(p => p.amount * p.price).reduce((prevVal, val) => prevVal + val);

    this.dialog
      .open(PurchasesConfirmationDialog, {data: {total}})
      .afterClosed()
      .subscribe(confirmation => {
        confirmation && this.purchasesService.addPurchaseGroup(purchaseGroup).subscribe(
          () => this.snackBar.open("Purchases saved", "dismiss", { duration: 3000 })
        );
      });
  }

  openAddShopDialog(): void {
    this.dialog
      .open(AddPurchaseShopDialog, {data: {name: ""}})
      .afterClosed()
      .pipe(filter(isNonNull))
      .subscribe((savedShop: ShopDto) => {
        this.updateAvailableShops(savedShop);
        this.selectShop(savedShop);
      });
  }

  selectShop(shop: ShopDto): void {
    this.purchasesForm.patchValue({shop: shop});
  }

  updateAvailableShops(...shops: ShopDto[]): void {
    this.shops = this.shops.concat(shops);
  }
}

