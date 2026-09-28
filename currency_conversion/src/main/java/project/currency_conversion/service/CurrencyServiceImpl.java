package project.currency_conversion.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import project.currency_conversion.dto.ConversionResponse;
import project.currency_conversion.client.ExchangeRateClient;
import project.currency_conversion.dto.ConversionRequest;
import project.currency_conversion.dto.ExchangeRateApiResponse;
import project.currency_conversion.repository.ConversionHistoryRepository;
import project.currency_conversion.document.ConversionHistory;

@Service
public class CurrencyServiceImpl implements CurrencyService {

  private final ExchangeRateClient exchangeRateClient;
  private final ConversionHistoryRepository conversionHistoryRepository;

  public CurrencyServiceImpl(ExchangeRateClient exchangeRateClient, ConversionHistoryRepository conversionHistoryRepository) {
    this.exchangeRateClient = exchangeRateClient;
    this.conversionHistoryRepository = conversionHistoryRepository;
  }

  @Override
  public ConversionResponse convert(ConversionRequest request) {
    ExchangeRateApiResponse exchangeRateApiResponse = exchangeRateClient.getRates();
    Map<String, Double> rates = exchangeRateApiResponse.getRates();

    if (rates == null || !rates.containsKey(request.getFrom()) || !rates.containsKey(request.getTo())) {
      throw new IllegalArgumentException("Unsupported currency code: " + request.getFrom() + " or " + request.getTo());
    }

    double fromRate = rates.get(request.getFrom());
    double toRate = rates.get(request.getTo());
    double originAmount = request.getAmount();
    double rateUsed = toRate / fromRate;
    double convertedAmount = originAmount * rateUsed;

    ConversionResponse conversionResponse = new ConversionResponse(request.getFrom(), request.getTo(), rateUsed, originAmount, convertedAmount);
         
    ConversionHistory conversionHistory = new ConversionHistory(request.getFrom(), request.getTo(), rateUsed, originAmount, convertedAmount);
    conversionHistory.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    conversionHistoryRepository.save(conversionHistory);

    return conversionResponse;
  }

  @Override
  public List<ConversionHistory> getHistory() {
    List<ConversionHistory> history = conversionHistoryRepository.findAll();
    Collections.reverse(history);
    return history.size() > 10 ? history.subList(0, 10) : history;
  }

  @Override
  public Set<String> getSupportedCurrencies() {
    ExchangeRateApiResponse exchangeRateApiResponse = exchangeRateClient.getRates();
    if (exchangeRateApiResponse != null && exchangeRateApiResponse.getRates() != null) {
      return exchangeRateApiResponse.getRates().keySet();
    }
    return Set.of("USD", "EUR", "GBP", "INR", "JPY", "AUD", "CAD", "CHF", "CNY");
  }
}