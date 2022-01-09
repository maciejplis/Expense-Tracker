import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'add-purchase-shop-dialog',
  templateUrl: 'add-purchase-shop-dialog.component.html',
  styleUrls: ['./add-purchase-shop-dialog.component.scss']
})
export class AddPurchaseShopDialog {
  constructor(
    public dialogRef: MatDialogRef<AddPurchaseShopDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}

export interface DialogData {
  name: string;
}

