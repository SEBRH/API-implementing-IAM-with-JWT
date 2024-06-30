package group.name.IamAPI.iam.domain.model.exceptions;

public class CustomUseAlreadyTakenException extends IllegalArgumentException{
    public CustomUseAlreadyTakenException(String username) {
        super( "So sorry! the username: " + username + " has already been taken. Please try another one.");
    }
}
