package project.currency_conversion.dto;

public class ConversionResponse {
    
   private String from;
   private String to;
    
   private double originAmount;
   private double convertedAmount;
   private double rateUsed;

   public ConversionResponse(String from, String to, double rateUsed, double originAmount, double convertedAmount) {
     
        this.from = from;
        this.to = to;
        this.rateUsed = rateUsed;
        this.originAmount = originAmount;
        this.convertedAmount = convertedAmount;
    }
    public String getFrom() {
          return from;
     }
    
     public void setFrom(String from) {
          this.from = from;
     }
    
     public String getTo() {
          return to;
     }
    
     public double getOriginAmount() {
          return originAmount;
     }
    
     public double getConvertedAmount() {
          return convertedAmount;
     }
  
    
     public double getRateUsed() {
          return rateUsed;
     }
}
