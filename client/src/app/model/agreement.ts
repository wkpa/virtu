import {Client} from "./client";
import {Address} from "./address";
import {CalculationData} from "./calculationData";
import {CalculationResult} from "./calculationResult";

export class Agreement {
  id: number;
  agreementNumber: string;
  agreementDate: Date;
  client: Client;
  address: Address;
  calculationData: CalculationData;
  calculationResult: CalculationResult;

  constructor() {
    this.client = new Client();
    this.address = new Address();
    this.calculationData = new CalculationData();
    this.calculationResult = new CalculationResult();
  }
}
