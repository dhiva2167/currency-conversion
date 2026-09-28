package project.currency_conversion.controller;

import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.currency_conversion.dto.ConversionRequest;
import project.currency_conversion.dto.ConversionResponse;
import project.currency_conversion.document.ConversionHistory;
import project.currency_conversion.service.CurrencyService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/convert")
    public ConversionResponse convert(@RequestBody ConversionRequest request) {
        return currencyService.convert(request);
    }

    @GetMapping("/history")
    public List<ConversionHistory> getHistory() {
        return currencyService.getHistory();
    }

    @GetMapping("/currencies")
    public Set<String> getSupportedCurrencies() {
        return currencyService.getSupportedCurrencies();
    }
}
