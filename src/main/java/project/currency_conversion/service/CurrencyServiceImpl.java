package project.currency_conversion.service;

import java.util.Map;
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

  public CurrencyServiceImpl(ExchangeRateClient exchangeRateClient , ConversionHistoryRepository conversionHistoryRepository ) {
   
    this.exchangeRateClient = exchangeRateClient;
    this.conversionHistoryRepository = conversionHistoryRepository;
  }
     @Override
    public ConversionResponse convert(ConversionRequest request) {
        
      ExchangeRateApiResponse exchangeRateApiResponse = exchangeRateClient.getRates();
      Map<String, Double> rates = exchangeRateApiResponse.getRates();

      double fromRate = rates.get(request.getFrom());
      double toRate = rates.get(request.getTo());
      double originAmount = request.getAmount();
      double rateUsed = toRate / fromRate;
      double convertedAmount = originAmount * rateUsed;

      ConversionResponse conversionResponse = new ConversionResponse(request.getFrom(), request.getTo(), rateUsed, originAmount, convertedAmount);
           
      ConversionHistory conversionHistory = new ConversionHistory(request.getFrom(), request.getTo(), rateUsed, originAmount, convertedAmount);
      conversionHistoryRepository.save(conversionHistory);

        return conversionResponse;
    }
}