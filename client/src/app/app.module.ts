import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from "@angular/forms";
import { NgModule } from '@angular/core';
import { HttpClientModule } from "@angular/common/http";


import { AppComponent } from './app.component';
import { AgreementListComponent } from './agreement-list/agreement-list.component';
import { AgreementFormComponent } from './agreement-form/agreement-form.component';
import { AgreementService } from './service/agreement.service';
import { AppRoutingModule } from "./app-routing.module";
import { ClientSelectFormComponent } from './client-select-form/client-select-form.component';
import { ClientService } from "./service/client.service";
import { AssetKindService } from "./service/asset-kind.service";
import { CalculateService } from "./service/calculate.service";


@NgModule({
  declarations: [
    AppComponent,
    AgreementListComponent,
    AgreementFormComponent,
    ClientSelectFormComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [AgreementService, ClientService, AssetKindService, CalculateService],
  bootstrap: [AppComponent]
})
export class AppModule { }
