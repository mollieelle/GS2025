import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface MutualFund {
  ticker: string;
  name: string;
  selected: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class MutualFundService {
  private apiUrl = 'http://localhost:8080/api/mutualfunds';

  constructor(private http: HttpClient) { }

  getMutualFunds(): Observable<MutualFund[]> {
    return this.http.get<MutualFund[]>(this.apiUrl);
  }
}