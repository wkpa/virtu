import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Agreement } from '../model/agreement';

@Injectable()
export class AgreementService {

  private readonly agreementsUrl: string;

  constructor(private http: HttpClient) {
    this.agreementsUrl = '/api/agreements';
  }

  public findAll(): Observable<{status: string, data: Agreement[]}> {
    return this.http.get <{status: string, data: Agreement[]}>(this.agreementsUrl);
  }

  public save(agreement: Agreement) {
    return this.http.post<{status: string, data: Agreement}>(this.agreementsUrl, agreement);
  }
}
