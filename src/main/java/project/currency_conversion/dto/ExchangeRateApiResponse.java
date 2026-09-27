package project.currency_conversion.dto;

import project.currency_conversion.dto.ExchangeRateApiResponse;
import java.util.Map;
import java.io.Serializable;

public class ExchangeRateApiResponse implements Serializable {
    private String base;
    private String disclaimer;
    private long timestamp;
    private Map<String, Double> rates;
    private String license;

    
    public String getLicense() {
        return license;
    }
    public void setLicense(String license) {
        this.license = license;
    }
    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}