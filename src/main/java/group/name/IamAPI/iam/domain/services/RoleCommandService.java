package group.name.IamAPI.iam.domain.services;

import group.name.IamAPI.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
