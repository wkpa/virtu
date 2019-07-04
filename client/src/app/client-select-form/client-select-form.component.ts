import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { Client } from "../model/client";
import {ClientService} from "../service/client.service";

@Component({
  selector: 'app-client-select-form',
  templateUrl: './client-select-form.component.html',
  styleUrls: ['./client-select-form.component.css']
})
export class ClientSelectFormComponent implements OnInit {

  clients: Client[];
  client: Client;

  constructor(private clientService: ClientService, private router: Router) {
    this.client = new Client();
  }

  ngOnInit() {
  }

  public find() {
    this.clients = [];
    this.clientService.findClients(this.client).subscribe(data => {
      if (data.status === "OK") {
        this.clients = data.data;
      }
    });
  }

  public selectClient(client: Client) {
    this.clientService.clientSelected(client);
    this.router.navigate([{ outlets: { popup: null }}]);
    // this.router.navigate(['addagreement']);
  }
}
