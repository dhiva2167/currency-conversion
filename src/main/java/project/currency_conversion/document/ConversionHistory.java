package project.currency_conversion.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "conversion_history")
public class ConversionHistory {

       public ConversionHistory(String from , String to , double rateUsed,double originAmount,double convertedAmount)
       {
            this.convertedAmount = convertedAmount;
            this. originAmount =  originAmount;
            this.rateUsed = rateUsed;
       }
        @Id
        private String id;
        private String from;
        private String to;
        private double originAmount;
        private double convertedAmount;
        private double rateUsed;
        private String timestamp;
    
       public String getFrom()
       {
        return from;
       }
       public void setFrom(String from)
       {
        this.from = from;
       }
       public String getId()
       {
        return id;
       }
       public void setId(String id)
       {
        this.id = id;
       }
         public String getTo()
         {
          return to;
         }
         public void setTo(String to)
         {
          this.to = to;
         }
        public double getOriginAmount()
        {
          return originAmount;
        }
        public void setOriginAmount(double originAmount)
        {
          this.originAmount = originAmount;
        }
        public double getConvertedAmount()
        {
          return convertedAmount;
        }
        public void setConvertedAmount(double convertedAmount)
        {
          this.convertedAmount = convertedAmount;
        }
        public double getRateUsed()
        {
          return rateUsed;
        }
        public void setRateUsed(double rateUsed)
        {
          this.rateUsed = rateUsed;
        }
        public String getTimestamp()
        {
          return timestamp;
        }
        public void setTimestamp(String timestamp)
        {
          this.timestamp = timestamp;
        }

}
