package project.currency_conversion.service;

import project.currency_conversion.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.currency_conversion.document.User;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

      private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
   
       public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
       }
      @Override
       public User register(User user) {
        
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);



       }
       

}
