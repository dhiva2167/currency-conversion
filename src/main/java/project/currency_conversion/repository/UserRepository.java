package project.currency_conversion.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.currency_conversion.document.User;

public interface UserRepository extends MongoRepository<User, String> {

      User findByEmail(String email);
    
} 
