import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface FutureValueRequest {
  ticker: string;
  initialInvestment: number;
  timeInYears: number;
}

export interface FutureValueResponse {
  ticker: string;
  riskFreeRate: number;
  returnRate: number;
  mutualFundBeta: number;
  earnings: number;
  totalBalance: number;
}

@Injectable({
  providedIn: 'root'
})
export class CalculationService {
  private apiUrl = 'http://localhost:8080/api/mutualfunds/future-value'; // Replace with your backend URL

  constructor(private http: HttpClient) { }

  calculateFutureValue(request: FutureValueRequest): Observable<FutureValueResponse> {
    return this.http.post<FutureValueResponse>(this.apiUrl, request);
  }
}