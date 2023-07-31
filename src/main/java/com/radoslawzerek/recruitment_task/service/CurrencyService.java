package com.radoslawzerek.recruitment_task.service;

import com.radoslawzerek.recruitment_task.entity.RequestInfo;
import com.radoslawzerek.recruitment_task.model.CurrencyRequest;
import com.radoslawzerek.recruitment_task.model.CurrencyResponse;

import java.util.List;

public interface CurrencyService {

    CurrencyResponse getCurrentCurrencyValue(CurrencyRequest currencyRequest);

    List<RequestInfo> getAllRequests();
}
