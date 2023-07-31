import { Component } from '@angular/core';
import { CurrencyService } from '../currency.service';
import { CurrencyRequest, CurrencyResponse, ResponseInfo } from './currency-info.model';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-currency-info',
  templateUrl: './currency-info.component.html',
  styleUrls: ['./currency-info.component.css']
})
export class CurrencyInfoComponent {

  currencyCode = '';

  userName = '';

  currencyResponse: CurrencyResponse | null = null;

  requests: ResponseInfo[] = [];

  userNameFormControl = new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z ]+$')]);

  errorMsg?: string;

  constructor(private currencyService: CurrencyService) { }

  getCurrencyValue() {
    const currencyRequest: CurrencyRequest = {
      currency: this.currencyCode,
      name: this.userName
    };

    this.currencyService.getCurrentCurrencyValue(currencyRequest).subscribe(
      (response) => {
        this.currencyResponse = response;
        this.getCurrencyRequests();

        if (response.hasOwnProperty('error')) {
          this.errorMsg = response.error;
        } else {
          this.currencyResponse = response;
        }
      },
      (error) => {
        console.error('Error fetching currency value:', error);
      }
    );
  }

  getCurrencyRequests() {
    this.currencyService.getAllRequests().subscribe(
      (requests) => {
        this.requests = requests;
      },
      (error) => {
        console.error('Error fetching currency requests:', error);
      }
    );
  }
}
