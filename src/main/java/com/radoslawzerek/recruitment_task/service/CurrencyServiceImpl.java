package com.radoslawzerek.recruitment_task.service;

import com.radoslawzerek.recruitment_task.entity.RequestInfo;
import com.radoslawzerek.recruitment_task.exception.CurrencyNotFoundException;
import com.radoslawzerek.recruitment_task.model.ApiResponse;
import com.radoslawzerek.recruitment_task.model.CurrencyRate;
import com.radoslawzerek.recruitment_task.model.CurrencyRequest;
import com.radoslawzerek.recruitment_task.model.CurrencyResponse;
import com.radoslawzerek.recruitment_task.repository.RequestInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    private final RequestInfoRepository requestInfoRepository;
    private final String nbpApiUrl;
    private final RestTemplate restTemplate;
    @Autowired
    public CurrencyServiceImpl(RequestInfoRepository requestInfoRepository,
                               @Qualifier("getNbpApiUrl") String nbpApiUrl,
                               RestTemplate restTemplate) {
        this.requestInfoRepository = requestInfoRepository;
        this.nbpApiUrl = nbpApiUrl;
        this.restTemplate = restTemplate;
    }
    @Override
    public CurrencyResponse getCurrentCurrencyValue(CurrencyRequest currencyRequest) {
        try {
            String currencyCode = currencyRequest.getCurrency();
            String name = currencyRequest.getUser();

            ResponseEntity<ApiResponse[]> responseEntity = restTemplate.exchange(
                    nbpApiUrl,
                    HttpMethod.GET,
                    null,
                    ApiResponse[].class
            );

            List<CurrencyRate> rates = Objects.requireNonNull(responseEntity.getBody())[0].getRates();

            Double rate = null;
            for (CurrencyRate currencyRate : rates) {
                if (currencyRate.getCode().equalsIgnoreCase(currencyCode)) {
                    rate = currencyRate.getMid();
                    break;
                }
            }

            if (rate == null) {

                CurrencyResponse errorResponse = new CurrencyResponse();
                errorResponse.setError("Currency not found");
                return errorResponse;
            }

            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setCurrency(currencyCode);
            requestInfo.setName(name);
            requestInfo.setRequestTime(LocalDateTime.now());
            requestInfo.setCurrencyValue(rate);
            requestInfoRepository.save(requestInfo);

            return new CurrencyResponse(rate);
        } catch (CurrencyNotFoundException e) {
            logger.error("CurrencyNotFoundException occurred: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An unexpected exception occurred: ", e);
            throw new RuntimeException("Error processing currency request");
        }
    }

    @Override
    public List<RequestInfo> getAllRequests() {
        return requestInfoRepository.findAll();
    }
}
