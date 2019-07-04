import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { CalculationData } from "../model/calculationData";
import { CalculationResult } from "../model/calculationResult";

@Injectable()
export class CalculateService {

  private readonly calculationUrl: string;

  constructor(private http: HttpClient) {
    this.calculationUrl = '/api/calculate';
  }

  public getInsurancePremium(calculationData: CalculationData) {
    return this.http.post<{status: string, data: CalculationResult}>(this.calculationUrl, calculationData);
  }
}
