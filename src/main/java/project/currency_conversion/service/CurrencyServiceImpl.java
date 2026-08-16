package project.currency_conversion.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import project.currency_conversion.dto.ConversionResponse;
import project.currency_conversion.client.ExchangeRateClient;
import project.currency_conversion.dto.ConversionRequest;
import project.currency_conversion.dto.ExchangeRateApiResponse;



@Service
public class CurrencyServiceImpl implements CurrencyService {

  private final ExchangeRateClient exchangeRateClient;

  public CurrencyServiceImpl(ExchangeRateClient exchangeRateClient) {
    this.exchangeRateClient = exchangeRateClient;
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
           
      

        return conversionResponse;
    }
}