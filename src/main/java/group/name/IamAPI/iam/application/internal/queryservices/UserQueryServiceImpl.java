package group.name.IamAPI.iam.application.internal.queryservices;

import group.name.IamAPI.iam.domain.model.aggregates.User;
import group.name.IamAPI.iam.domain.model.entities.Role;
import group.name.IamAPI.iam.domain.model.queries.GetAllUsersQuery;
import group.name.IamAPI.iam.domain.model.queries.GetUserByIdQuery;
import group.name.IamAPI.iam.domain.model.queries.GetUserByUsernameQuery;
import group.name.IamAPI.iam.domain.model.queries.GetUsersByRoleQuery;
import group.name.IamAPI.iam.domain.model.valueobjects.Roles;
import group.name.IamAPI.iam.domain.services.UserQueryService;
import group.name.IamAPI.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import group.name.IamAPI.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserQueryServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }



    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    @Override
    public List<User> handle(GetUsersByRoleQuery query) {
        Optional<Role> roleOptional = roleRepository.findByName(Roles.valueOf(query.role()));
        if (roleOptional.isPresent()) {
            Set<Role> roles = Collections.singleton(roleOptional.get());
            return userRepository.getAllByRoles(roles);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userid());
    }

    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return userRepository.findByUsername(query.username());
    }

}
