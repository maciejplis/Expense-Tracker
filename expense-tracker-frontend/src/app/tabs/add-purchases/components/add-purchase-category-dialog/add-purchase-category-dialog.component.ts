import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {CategoriesService, CategoryDto} from 'build/expense-tracker-frontend-api';
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {ErrorMessages} from "../../../../common/pipes/error-messages-converter.pipe";

@Component({
  selector: 'add-purchase-shop-dialog',
  templateUrl: 'add-purchase-category-dialog.component.html',
  styleUrls: ['./add-purchase-category-dialog.component.scss']
})
export class AddPurchaseCategoryDialog implements OnInit {

  categoryForm: FormControl;
  errorMessages: ErrorMessages = {
    required: "This field is required",
    minlength: "Category name must contain at least 3 characters",
    maxlength: "Category name must contain at most 24 characters",
    pattern: "Illegal characters",
    conflict: "Category name already exists",
    unknown: "Unknown error occurred",
  }

  constructor(
    private dialogRef: MatDialogRef<AddPurchaseCategoryDialog>,
    private categoriesService: CategoriesService,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit(): void {
    this.categoryForm = this.formBuilder.control("", [
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
      .subscribe({
        next: (savedCategory: CategoryDto) => this.dialogRef.close(savedCategory),
        error: (errResp: HttpErrorResponse) => {
          console.error(errResp);
          this.categoryForm.setErrors(errResp.status == 409 ? {conflict: true} : {unknown: true});
        }
      });
  }
}
