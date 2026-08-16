package project.currency_conversion.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.currency_conversion.dto.ConversionRequest;
import project.currency_conversion.dto.ConversionResponse;
import project.currency_conversion.service.CurrencyService;

@RestController
@RequestMapping("/api/convert")
public class CurrencyController {

     private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
    this.currencyService = currencyService;
}
  @PostMapping
  public ConversionResponse convert(@RequestBody ConversionRequest request) {
        return currencyService.convert(request);
        
    }
}
