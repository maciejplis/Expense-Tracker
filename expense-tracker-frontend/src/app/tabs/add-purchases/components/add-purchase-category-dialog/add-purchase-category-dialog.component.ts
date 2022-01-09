import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'add-purchase-shop-dialog',
  templateUrl: 'add-purchase-category-dialog.component.html',
  styleUrls: ['./add-purchase-category-dialog.component.scss']
})
export class AddPurchaseCategoryDialog {
  constructor(
    public dialogRef: MatDialogRef<AddPurchaseCategoryDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}

export interface DialogData {
  name: string;
}

