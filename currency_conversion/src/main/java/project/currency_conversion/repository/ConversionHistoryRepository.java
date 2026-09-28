package project.currency_conversion.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.currency_conversion.document.ConversionHistory;

public interface ConversionHistoryRepository extends MongoRepository<ConversionHistory, String> {
       
}
 