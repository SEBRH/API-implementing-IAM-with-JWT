package group.name.IamAPI.iam.domain.model.queries;


import group.name.IamAPI.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles roleName) {
}
