export interface CurrencyRequest {

  currency: string;

  name: string;
}

export interface CurrencyResponse {

  value: number;

  error: string;
}

export interface ResponseInfo {

  currency: string;

  user: string;

  currencyValue: number;

  requestTime: string;
}


