import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';

import {HotTableModule} from '@handsontable/angular';
import {registerAllModules} from 'handsontable/registry';
import {ExportFile, registerPlugin, Search} from "handsontable/plugins";
import {plPL, registerLanguageDictionary} from 'handsontable/i18n';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NavComponent} from './nav/nav.component';
import {LayoutModule} from '@angular/cdk/layout';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatNativeDateModule} from '@angular/material/core';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list'
import {MatSelectModule} from "@angular/material/select";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from "@angular/material/card";
import {ApiModule} from 'build/expense-tracker-frontend-api/api.module'
import {HttpClientModule} from "@angular/common/http";
import {AddPurchasesComponent} from './tabs/add-purchases/add-purchases.component';
import {
  PurchasesInputTableComponent
} from './tabs/add-purchases/components/purchases-input-table/purchases-input-table.component';
import {DashboardComponent} from './tabs/dashboard/dashboard.component';
import {
  AddPurchaseShopDialog
} from './tabs/add-purchases/components/add-purchase-shop-dialog/add-purchase-shop-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {
  AddPurchaseCategoryDialog
} from "./tabs/add-purchases/components/add-purchase-category-dialog/add-purchase-category-dialog.component";
import {Configuration} from 'build/expense-tracker-frontend-api';
import {environment} from "../environments/environment";
import {
  PurchasesConfirmationDialog
} from './tabs/add-purchases/components/purchases-confirmation-dialog/purchases-confirmation-dialog.component';
import '@angular/common/locales/global/pl'
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {ErrorMessagesConverterPipe} from './common/pipes/error-messages-converter.pipe';

registerAllModules()
registerPlugin(ExportFile);
registerPlugin(Search);
registerLanguageDictionary(plPL)


@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    AddPurchasesComponent,
    PurchasesInputTableComponent,
    DashboardComponent,
    AddPurchaseShopDialog,
    AddPurchaseCategoryDialog,
    PurchasesConfirmationDialog,
    ErrorMessagesConverterPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HotTableModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatNativeDateModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatSelectModule,
    MatDatepickerModule,
    MatCardModule,
    ApiModule,
    HttpClientModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    MatSnackBarModule
  ],
  providers: [
    {
      provide: Configuration,
      useFactory: () => new Configuration({basePath: environment.basePath})
    }
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule {
}
