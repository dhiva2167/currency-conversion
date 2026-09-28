package project.currency_conversion.service;

import java.util.List;
import java.util.Set;
import project.currency_conversion.dto.ConversionResponse;
import project.currency_conversion.dto.ConversionRequest;
import project.currency_conversion.document.ConversionHistory;

public interface CurrencyService {
    ConversionResponse convert(ConversionRequest request);
    List<ConversionHistory> getHistory();
    Set<String> getSupportedCurrencies();
}

