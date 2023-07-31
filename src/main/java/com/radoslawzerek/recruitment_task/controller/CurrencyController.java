package com.radoslawzerek.recruitment_task.controller;

import com.radoslawzerek.recruitment_task.entity.RequestInfo;
import com.radoslawzerek.recruitment_task.model.CurrencyRequest;
import com.radoslawzerek.recruitment_task.model.CurrencyResponse;
import com.radoslawzerek.recruitment_task.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/get-current-currency-value-command")
    public ResponseEntity<CurrencyResponse> getCurrentCurrencyValue(@RequestBody CurrencyRequest currencyRequest) {
        try {
            CurrencyResponse response = currencyService.getCurrentCurrencyValue(currencyRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/requests")
    public ResponseEntity<List<RequestInfo>> getAllRequests() {
        List<RequestInfo> requests = currencyService.getAllRequests();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }
}
