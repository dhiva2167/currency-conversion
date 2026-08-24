package project.currency_conversion.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.currency_conversion.dto.ConversionRequest;
import project.currency_conversion.dto.ConversionResponse;
import project.currency_conversion.service.AuthService;
import project.currency_conversion.service.CurrencyService;
import project.currency_conversion.document.User;
import project.currency_conversion.dto.UserResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
      private final AuthService authService;

      public AuthController(AuthService authService) {
        this.authService = authService;
     } 

    @PostMapping("/register")
        public UserResponse register(@RequestBody User user) {
            User savedUser = authService.register(user);
            return new UserResponse(savedUser.getId(), savedUser.getEmail()); 
}
}