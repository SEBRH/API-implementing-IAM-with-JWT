package group.name.IamAPI.iam.application.internal.queryservices;

import group.name.IamAPI.iam.domain.model.entities.Role;
import group.name.IamAPI.iam.domain.model.queries.GetAllRolesQuery;
import group.name.IamAPI.iam.domain.model.queries.GetRoleByNameQuery;
import group.name.IamAPI.iam.domain.services.RoleQueryService;
import group.name.IamAPI.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleQueryServiceImpl implements RoleQueryService {
private final RoleRepository roleRepository;

    public RoleQueryServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> handle(GetAllRolesQuery query) {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> handle(GetRoleByNameQuery query) {
        return roleRepository.findByName(query.roleName());
    }

}
