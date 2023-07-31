package com.radoslawzerek.recruitment_task.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRate {

    private String currency;

    private String code;

    private Double mid;
}
