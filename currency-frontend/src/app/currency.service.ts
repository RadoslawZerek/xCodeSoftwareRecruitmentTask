import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CurrencyRequest, CurrencyResponse, ResponseInfo } from './currency-info/currency-info.model';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {
  private baseUrl = 'http://localhost:8080/currencies';

  constructor(private http: HttpClient) { }

  getCurrentCurrencyValue(currencyRequest: CurrencyRequest): Observable<CurrencyResponse> {
    const url = `${this.baseUrl}/get-current-currency-value-command`;
    return this.http.post<CurrencyResponse>(url, currencyRequest);
  }

  getAllRequests(): Observable<ResponseInfo[]> {
    const url = `${this.baseUrl}/requests`;
    return this.http.get<ResponseInfo[]>(url);
  }
}
