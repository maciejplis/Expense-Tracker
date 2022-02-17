import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {ShopDto, ShopsService} from 'build/expense-tracker-frontend-api';
import {ErrorMessages} from "../../../../common/pipes/error-messages-converter.pipe";

const {required, minLength, maxLength, pattern} = Validators;

@Component({
  selector: 'add-purchase-shop-dialog',
  templateUrl: 'add-purchase-shop-dialog.component.html',
  styleUrls: ['./add-purchase-shop-dialog.component.scss']
})
export class AddPurchaseShopDialog implements OnInit {

  shopForm: FormControl;

  errorMessages: ErrorMessages = {
    required: "This field is required",
    minlength: "Shop name must contain at least 2 characters",
    maxlength: "Shop name must contain at most 24 characters",
    pattern: "Illegal characters",
    conflict: "Shop name already exists",
    unknown: "Unknown error occurred",
  }

  constructor(
    private dialogRef: MatDialogRef<AddPurchaseShopDialog>,
    private shopsService: ShopsService,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit() {
    this.shopForm = this.formBuilder.control("", [
      required,
      minLength(2),
      maxLength(24),
      pattern("^[a-zA-Z0-9-_ ]*$")
    ]);
  }

  onSave(): void {
    if (this.shopForm.invalid) {
      return;
    }

    this.shopsService
      .addPurchaseShop({id: "", name: this.shopForm.value})
      .subscribe({
        next: (savedShop: ShopDto) => this.dialogRef.close(savedShop),
        error: (errResp: HttpErrorResponse) => {
          console.error(errResp);
          this.shopForm.setErrors(errResp.status == 409 ? {conflict: true} : {unknown: true});
        }
      });
  }
}
