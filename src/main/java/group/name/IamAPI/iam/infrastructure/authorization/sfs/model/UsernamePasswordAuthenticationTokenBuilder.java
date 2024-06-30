package group.name.IamAPI.iam.infrastructure.authorization.sfs.model;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

/***
 * This class is responsible for building a UsernamePasswordAuthenticationToken object.
 * The UsernamePasswordAuthenticationToken object is used to represent the authentication token of a user.
 * The authentication token is used to authenticate the user.
 * The UsernamePasswordAuthenticationTokenBuilder class is used to build a UsernamePasswordAuthenticationToken
 * object from a UserDetails object and a HttpServletRequest object.
 */

public class UsernamePasswordAuthenticationTokenBuilder {
    public static UsernamePasswordAuthenticationToken build(UserDetails principal, HttpServletRequest request) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(principal,
                null,
                principal.getAuthorities());

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return usernamePasswordAuthenticationToken;
    }
}
