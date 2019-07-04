import {AssetKind} from "./assetKind";

export class CalculationData {
  insurancePayment: number;
  periodFrom: Date;
  periodTo: Date;
  yearOfBuild: number;
  assetKind: AssetKind;
  area: number;

  constructor() {
    this.assetKind = new AssetKind();
  }

}

