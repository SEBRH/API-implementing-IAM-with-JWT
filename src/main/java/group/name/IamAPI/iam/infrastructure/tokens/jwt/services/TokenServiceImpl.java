package group.name.IamAPI.iam.infrastructure.tokens.jwt.services;

import group.name.IamAPI.iam.application.internal.outboundservices.tokens.TokenService;
import group.name.IamAPI.iam.infrastructure.tokens.jwt.BearerTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/***
 * TokenServiceImpl class.
 *
 */

@Service
public class TokenServiceImpl implements BearerTokenService {
    private final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);
    private static final String AUTHORIZATION_PARAMETER_NAME = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private static final int TOKEN_BEGIN_INDEX = 7;

    /***
     * secret field.
     * this should be a secret key. that is in the application.properties file.
     * this is used to sign the jwt token.
     * @value ${authorization.jwt.secret}
     * basically this is a secret key. that is used to sign the jwt token.
     */

    @Value("${authorization.jwt.secret}")
    private String secret;

    /***
     * expirationDays field.
     * this is the expiration days of the jwt token.
     * @value ${authorization.jwt.expiration.days}
     * basically this is the expiration days of the jwt token.
     * Once the token is expired, the user will have to log in again.
     */

    @Value("${authorization.jwt.expiration.days}")
    private int expirationDays;

    /***
     * getBearerTokenFrom method.
     * this method is used to get the bearer token from the request.
     * it gets the authorization parameter from the request.
     * it checks if the token is present in the parameter and if the token is a bearer token.
     * if the token is present and is a bearer token, it extracts the token from the parameter and returns it.
     * @param request
     * @return String
     * basically this method is used to get the bearer token from the request.
     */

    @Override
    public String getBearerTokenFrom(HttpServletRequest request) {
        String parameter = getAuthorizationParameterFrom(request);
        if (isTokenPresentIn(parameter) && isBearerTokenIn(parameter))
            return extractTokenFrom(parameter);
        return null;
    }

    @Override
    public String generateToken(Authentication authentication) {
        return buildTokenWithDefaultParameters(authentication.getName());
    }

    @Override
    public String generateToken(String username) {
        return buildTokenWithDefaultParameters(username);
    }

    @Override
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /***
     * validateToken method.
     * this method is used to validate the token.
     * it tries to parse the token and verify it with the signing key.
     * if the token is valid, it logs that the token is valid and returns true.
     * if the token is invalid, it logs the error and returns false.
     * @param token
     * @return boolean
     * basically this method is used to validate the token.
     */

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            LOGGER.info("JSON Web Token is valid");
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JSON Web Token signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JSON Web Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JSON Web Token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JSON Web Token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JSON Web Token claims string is empty: {}", e.getMessage());
        }
        return false;
    }


    // private methods below these cant be accessed from outside the class.

    /***
     * getSigningKey method.
     * this method is used to get the signing key.
     * it converts the secret key to a byte array and then returns the hmacShaKeyFor of the keyBytes.
     * @return SecretKey
     */

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /***
     * This BUILDS the TOKEN with the default parameters.
     * this method is used to build the token with the default parameters.
     * it takes the username as a parameter.
     * it creates a new date object and sets it to the issuedAt.
     * it then adds the expiration days to the issuedAt date and sets it to the expiration.
     * it then gets the signing key and signs the token with the key.
     * @param username
     * @return String
     */

    private String buildTokenWithDefaultParameters(String username) {
        var issuedAt = new Date();
        var expiration = DateUtils.addDays(issuedAt, expirationDays);
        var key = getSigningKey();
        return Jwts.builder()
                .subject(username)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenPresentIn(String authorizationParameter) {
        return StringUtils.hasText(authorizationParameter);
    }

    private boolean isBearerTokenIn(String authorizationParameter) {
        return authorizationParameter.startsWith(BEARER_TOKEN_PREFIX);
    }

    private String extractTokenFrom(String authorizationHeaderParameter) {
        return authorizationHeaderParameter.substring(TOKEN_BEGIN_INDEX);
    }

    private String getAuthorizationParameterFrom(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_PARAMETER_NAME);
    }

}
