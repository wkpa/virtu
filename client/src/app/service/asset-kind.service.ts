import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

import { AssetKind } from "../model/assetKind";

@Injectable()
export class AssetKindService {

  private readonly assetKindUrl: string;

  constructor(private http: HttpClient) {
    this.assetKindUrl = '/api/assetkinds';
  }

  public getAssetKinds(): Observable<{status: string, data: AssetKind[]}> {
    return this.http.get <{status: string, data: AssetKind[]}>(this.assetKindUrl);
  }

}
