package group.name.IamAPI.iam.application.internal.commandservices;

import group.name.IamAPI.iam.domain.model.commands.SeedRolesCommand;
import group.name.IamAPI.iam.domain.model.entities.Role;
import group.name.IamAPI.iam.domain.model.valueobjects.Roles;
import group.name.IamAPI.iam.domain.services.RoleCommandService;
import group.name.IamAPI.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleCommandServiceImpl implements RoleCommandService {
    private final RoleRepository roleRepository;

    public RoleCommandServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void handle(SeedRolesCommand command) {
        Arrays.stream(Roles.values()).forEach(role -> {
            if(!roleRepository.existsByName(role)) {
                roleRepository.save(new Role(Roles.valueOf(role.name())));
            }
        });
    }

}
