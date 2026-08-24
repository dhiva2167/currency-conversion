package project.currency_conversion.dto;

public class UserResponse {
    
    private String id;
    private String email;

    public UserResponse(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
