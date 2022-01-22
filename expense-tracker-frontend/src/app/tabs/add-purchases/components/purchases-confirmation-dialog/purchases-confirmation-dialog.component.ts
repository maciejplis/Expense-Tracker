import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
  selector: 'app-purchases-confirmation-dialog',
  templateUrl: './purchases-confirmation-dialog.component.html',
  styleUrls: ['./purchases-confirmation-dialog.component.scss']
})
export class PurchasesConfirmationDialog {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

}
