package group.name.IamAPI.iam.infrastructure.authorization.sfs.configuration;

import group.name.IamAPI.iam.infrastructure.authorization.sfs.pipeline.BearerAuthorizationRequestFilter;
import group.name.IamAPI.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import group.name.IamAPI.iam.infrastructure.tokens.jwt.BearerTokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfiguration {
private final UserDetailsService userDetailsService;
private final BearerTokenService tokenService;
private final BCryptHashingService hashingService;
private final AuthenticationEntryPoint unauthorizedRequestHandler;

    public WebSecurityConfiguration(@Qualifier("defaultUserDetailsService") UserDetailsService userDetailsService, BearerTokenService tokenService, BCryptHashingService hashingService, AuthenticationEntryPoint unauthorizedRequestHandler) {
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
        this.hashingService = hashingService;
        this.unauthorizedRequestHandler = unauthorizedRequestHandler;
    }

    /***
     * This class is responsible for configuring the security of the application.
     *
     *
     */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /***
     * This method is responsible for creating an instance of the DaoAuthenticationProvider class.
     * basically, it is responsible for authenticating the user.
     */

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(hashingService);
        return authenticationProvider;
    }


    /***
     * This method is responsible for creating an instance of the PasswordEncoder class.
     * The PasswordEncoder class is used to encode the password of the user.
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return hashingService;
    }

    /***
     * This method is responsible for creating an instance of the BearerAuthorizationRequestFilter class.
     * The BearerAuthorizationRequestFilter class is used to filter the request and authenticate the user.
     */


    @Bean
    public BearerAuthorizationRequestFilter authorizationRequestFilter() {
        return new BearerAuthorizationRequestFilter(tokenService, userDetailsService);
    }

    /***
     * This method is responsible for creating an instance of the SecurityFilterChain class.
     * The SecurityFilterChain class is used to filter the request and authenticate the user.
     * The method is also responsible for configuring the security of the application.
     * The method is also responsible for configuring the CORS policy of the application.
     * basically, it is responsible for authenticating the user.
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CORS default configuration
        http.cors(configurer -> configurer.configurationSource( _ -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("*"));
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        }));
        http.csrf(csrfConfigurer -> csrfConfigurer.disable())
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedRequestHandler))
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(
                                "/api/v1/authentication/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .anyRequest().authenticated());
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authorizationRequestFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



}
