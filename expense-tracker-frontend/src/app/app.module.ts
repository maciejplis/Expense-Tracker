import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HotTableModule } from '@handsontable/angular';
import { registerAllModules } from 'handsontable/registry';
import { registerPlugin, ExportFile, Search } from "handsontable/plugins";
import { InputSpreadsheetComponent } from './input-spreadsheet/input-spreadsheet.component';
import  { registerLanguageDictionary, plPL } from 'handsontable/i18n';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavComponent } from './nav/nav.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatNativeDateModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list'
import {MatSelectModule} from "@angular/material/select";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from "@angular/material/card";


registerAllModules()
registerPlugin(ExportFile);
registerPlugin(Search);
registerLanguageDictionary(plPL)


@NgModule({
  declarations: [
    AppComponent,
    InputSpreadsheetComponent,
    NavComponent
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
    MatCardModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
