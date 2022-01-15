import {Component} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FormControl, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {ShopDto, ShopsService} from 'build/expense-tracker-frontend-api';

@Component({
  selector: 'add-purchase-shop-dialog',
  templateUrl: 'add-purchase-shop-dialog.component.html',
  styleUrls: ['./add-purchase-shop-dialog.component.scss']
})
export class AddPurchaseShopDialog {

  shopForm: FormControl;

  constructor(
    public dialogRef: MatDialogRef<AddPurchaseShopDialog>,
    private shopsService: ShopsService,
  ) {
    this.shopForm = new FormControl("", [
      Validators.required,
      Validators.minLength(2),
      Validators.maxLength(24),
      Validators.pattern("^[a-zA-Z0-9-_ ]*$")
    ]);
  }

  onSave(): void {
    if (this.shopForm.invalid) {
      return;
    }

    this.shopsService.addPurchaseShop({id: "", name: this.shopForm.value})
      .subscribe(
        (savedShop: ShopDto) => this.dialogRef.close(savedShop),
        (errResp: HttpErrorResponse) => {
          console.error(errResp);
          this.shopForm.setErrors(errResp.status == 409 ? {conflict: true} : {unknown: true});
        }
      );
  }
}

