import {EventEmitter, Injectable} from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Client } from "../model/client";

@Injectable()
export class ClientService {

  onClientSelected:EventEmitter<Client> = new EventEmitter();
  private readonly clientUrl: string;

  constructor(private http: HttpClient) {
    this.clientUrl = '/api/clients';
  }

  public findClients(client: Client ): Observable<{status: string, data: Client[]}> {
    let urlWithParam = this.clientUrl + '?';
    if (client.surname && client.surname !== "") {
      urlWithParam += ("surname=" + client.surname + "&");
    }
    if (client.name && client.name !== "") {
      urlWithParam += ("name=" + client.name + "&");
    }
    if (client.patronymic && client.patronymic !== "") {
      urlWithParam += ("patronymic=" + client.patronymic);
    }
    return this.http.get <{status: string, data: Client[]}>(urlWithParam);
  }

  public clientSelected(client: Client) {
    this.onClientSelected.emit(client);
  }

  public getClient(): Observable<{status: string, data: Client}> {
    return this.http.get <{status: string, data: Client}>(this.clientUrl);
  }

  public save(client: Client) {
    return this.http.post<Client>(this.clientUrl, client);
  }

}
