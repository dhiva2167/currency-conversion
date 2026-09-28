package project.currency_conversion.dto;

public class ConversionRequest {
    
      private String from;
      private String to;
      private double amount;

       public ConversionRequest() {
       }

      public ConversionRequest(String from, String to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
      }

      public String getFrom() {
        return from;
      }
    
      public String getTo() {
        return to;
      }

      public void setTo(String to) {
        this.to = to;
      }

      public double getAmount() {
        return amount;
      }

      public void setAmount(double amount) {
        this.amount = amount;
      }

}
