import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrencyInfoComponent } from './currency-info.component';

describe('CurrencyInfoComponent', () => {
  let component: CurrencyInfoComponent;
  let fixture: ComponentFixture<CurrencyInfoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CurrencyInfoComponent]
    });
    fixture = TestBed.createComponent(CurrencyInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
