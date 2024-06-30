package group.name.IamAPI.iam.application.internal.commandservices;

import group.name.IamAPI.iam.application.internal.outboundservices.hashing.HashingService;
import group.name.IamAPI.iam.application.internal.outboundservices.tokens.TokenService;
import group.name.IamAPI.iam.domain.model.aggregates.User;
import group.name.IamAPI.iam.domain.model.commands.SignInCommand;
import group.name.IamAPI.iam.domain.model.commands.SignUpCommand;
import group.name.IamAPI.iam.domain.model.entities.Role;
import group.name.IamAPI.iam.domain.model.valueobjects.Roles;
import group.name.IamAPI.iam.domain.services.UserCommandService;
import group.name.IamAPI.iam.domain.model.exceptions.CustomUseAlreadyTakenException;
import group.name.IamAPI.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import group.name.IamAPI.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    // This Method is used to handle the SignUpCommand
    // It checks if the username already exists in the database
    // If the username already exists, it throws a RuntimeException
    // It then maps the roles to the roles in the database
    // It then creates a new user with the username, password and roles
    // It then saves the user to the database
    // It then returns the user

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new CustomUseAlreadyTakenException(command.username());
        var stringRoles = command.roles();
        var roles = new ArrayList<Role>();
        if (stringRoles == null || stringRoles.isEmpty()) {
            var storedRole = roleRepository.findByName(Roles.ROLE_USER);
            storedRole.ifPresent(roles::add);

        } else {
            stringRoles.forEach(role -> {
                var storedRole = roleRepository.findByName(Roles.valueOf(role));
                storedRole.ifPresent(roles::add);
            });

        }

        var user = new User(command.username(), hashingService.encode(command.password()), roles);
        userRepository.save(user);
        return userRepository.findByUsername(command.username());
    }

    // This Method is used to handle the SignInCommand
    // It checks if the user exists in the database
    // If the user does not exist, it throws a RuntimeException
    // It then checks if the password is correct
    // If the password is incorrect, it throws a RuntimeException
    // It then generates a token for the user
    // It then returns the user and the token

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());
        System.out.println(user);
        if (user.isEmpty()) throw new IllegalArgumentException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new IllegalArgumentException("Invalid password");
        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

}
