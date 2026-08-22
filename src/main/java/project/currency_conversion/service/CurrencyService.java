package project.currency_conversion.service;

import project.currency_conversion.dto.ConversionResponse;
import project.currency_conversion.dto.ConversionRequest;

public interface CurrencyService {
    ConversionResponse convert(ConversionRequest request);
}

