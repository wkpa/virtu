import { Component, OnInit } from '@angular/core';
import { Agreement } from '../model/agreement';
import { AgreementService } from '../service/agreement.service';

@Component({
  selector: 'app-agreement-list',
  templateUrl: './agreement-list.component.html',
  styleUrls: ['./agreement-list.component.css']
})
export class AgreementListComponent implements OnInit {

  agreements: Agreement[];

  constructor(private agreementService: AgreementService) {
  }

  ngOnInit() {
    this.agreementService.findAll().subscribe(data => {
      if (data.status === "OK") {
        this.agreements = data.data;
      }
    });
  }

  editAgreement() {
  }

}
