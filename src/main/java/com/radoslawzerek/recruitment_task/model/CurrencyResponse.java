package com.radoslawzerek.recruitment_task.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyResponse {

    private Double value;

    private String error;

    public CurrencyResponse(Double value) {
        this.value = value;
    }
}
