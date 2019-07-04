import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AgreementService } from '../service/agreement.service';
import { Agreement } from '../model/agreement';
import { AssetKind } from "../model/assetKind";
import { AssetKindService } from "../service/asset-kind.service";
import { ClientService } from "../service/client.service";
import { CalculateService } from "../service/calculate.service";

@Component({
  selector: 'app-agreement-form',
  templateUrl: './agreement-form.component.html',
  styleUrls: ['./agreement-form.component.css']
})
export class AgreementFormComponent implements OnInit {

  agreement: Agreement;
  assetKinds: AssetKind[];
  selectedAssetKind: number;

  constructor(private route: ActivatedRoute, private router: Router, private agreementService: AgreementService,
              private assetKindService: AssetKindService, private clientService: ClientService,
              private calculateService: CalculateService) {
    this.agreement = new Agreement();
    this.agreement.agreementDate = new Date();
    this.clientService.onClientSelected.subscribe(client=>this.agreement.client = client);
  }

  ngOnInit() {
    this.assetKindService.getAssetKinds().subscribe(data => {
      if (data.status === "OK") {
        this.assetKinds = data.data;
      }
    });
  }

  save() {
    this.agreementService.save(this.agreement).subscribe(data => {
      if (data.status === "OK") {
        this.gotoAgreementList();
      }
    });
  }

  gotoAgreementList() {
    this.router.navigate(['agreements']);
  }

  selectClient() {
    this.router.navigate([{ outlets: { popup: [ 'findclients'] }}],{skipLocationChange: true});
  }

  editClient() {
  }

  calculate() {
    this.agreement.calculationData.assetKind = this.assetKinds.filter(x => x.id == this.selectedAssetKind)[0]
    this.calculateService.getInsurancePremium(this.agreement.calculationData).subscribe(data => {
      if (data.status === "OK") {
        this.agreement.calculationResult = data.data;
      }
    });
  }

}
