package group.name.IamAPI.iam.domain.services;

import group.name.IamAPI.iam.domain.model.aggregates.User;
import group.name.IamAPI.iam.domain.model.queries.GetAllUsersQuery;
import group.name.IamAPI.iam.domain.model.queries.GetUserByIdQuery;
import group.name.IamAPI.iam.domain.model.queries.GetUserByUsernameQuery;
import group.name.IamAPI.iam.domain.model.queries.GetUsersByRoleQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    List<User> handle(GetUsersByRoleQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);
}
