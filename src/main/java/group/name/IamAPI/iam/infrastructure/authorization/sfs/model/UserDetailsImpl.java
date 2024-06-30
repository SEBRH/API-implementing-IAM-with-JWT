package group.name.IamAPI.iam.infrastructure.authorization.sfs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import group.name.IamAPI.iam.domain.model.aggregates.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/***
 * This class implements the UserDetails interface from the Spring Security framework.
 * The UserDetailsImpl class is used to represent the details of a user.
 * Basically, it contains the user's username, password, authorities, and other information.
 * the authorities are the roles of the user
 * example: ADMIN, USER, etc
 * in this case, the authorities are a collection of SimpleGrantedAuthority objects
 * in simple terms, the authorities are the roles of the user
 * to configure the roles of the user, you can use the @PreAuthorize annotation
 * the @PreAuthorize annotation is used to specify the roles that are allowed to access a method
 * in a REST controller class or a service class we use
 * the @PreAuthorize annotation to specify the roles that are allowed to access a method
 */

@Getter
@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {
private final String username;
@JsonIgnore
private final String password;
private final boolean accountNonExpired;
private final boolean accountNonLocked;
private final boolean credentialsNonExpired;
private final boolean enabled;
private final Collection<? extends GrantedAuthority> authorities;

    /***
     * Constructor
     * @param username;
     * @param password;
     * @param authorities
     *
     * In this part of the code, the UserDetailsImpl class is defined.
     * This class implements the UserDetails interface from the Spring Security framework.
     * The UserDetailsImpl class is used to represent the details of a user.
     * Basically, it contains the user's username, password, authorities, and other information.
     */


public UserDetailsImpl(String username, String password,
                       Collection<? extends GrantedAuthority> authorities) {
    this.username = username;
    this.password = password;
    this.authorities = authorities;
    this.accountNonExpired = true;
    this.accountNonLocked = true;
    this.credentialsNonExpired = true;
    this.enabled = true;

}

    /***
     * This method is used to build a UserDetailsImpl object from a User object.
     * REMEMBER User is the name of the aggregate in domain/model/aggregates
     * the para user is the user object
     * @param user
     * @return
     */

    public static UserDetailsImpl build(User user) {
        var authorities = user.getRoles().stream().map(role -> role.getName().name())
                .map(SimpleGrantedAuthority::new).toList();
        return new UserDetailsImpl(user.getUsername(), user.getPassword(), authorities);
    }

}
