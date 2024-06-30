package group.name.IamAPI.iam.domain.services;

import group.name.IamAPI.iam.domain.model.entities.Role;
import group.name.IamAPI.iam.domain.model.queries.GetAllRolesQuery;
import group.name.IamAPI.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService  {
List<Role>handle(GetAllRolesQuery query);
Optional<Role>handle(GetRoleByNameQuery query);
}
