import {Component} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {CategoriesService, CategoryDto} from 'build/expense-tracker-frontend-api';
import {FormControl, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'add-purchase-shop-dialog',
  templateUrl: 'add-purchase-category-dialog.component.html',
  styleUrls: ['./add-purchase-category-dialog.component.scss']
})
export class AddPurchaseCategoryDialog {

  categoryForm: FormControl;

  constructor(
    private dialogRef: MatDialogRef<AddPurchaseCategoryDialog>,
    private categoriesService: CategoriesService
  ) {
    this.categoryForm = new FormControl("", [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(24),
      Validators.pattern("^[a-zA-Z0-9-_ ]*$")
    ]);
  }

  onSave(): void {
    if (this.categoryForm.invalid) {
      return;
    }

    this.categoriesService
      .addPurchaseCategory({id: "", name: this.categoryForm.value})
      .subscribe(
        (savedCategory: CategoryDto) => this.dialogRef.close(savedCategory),
        (errResp: HttpErrorResponse) => {
          console.error(errResp);
          this.categoryForm.setErrors(errResp.status == 409 ? {conflict: true} : {unknown: true});
        }
      );
  }
}
