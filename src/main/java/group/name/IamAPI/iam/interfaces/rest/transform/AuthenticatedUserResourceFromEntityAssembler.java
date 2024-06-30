package group.name.IamAPI.iam.interfaces.rest.transform;

import group.name.IamAPI.iam.domain.model.aggregates.User;
import group.name.IamAPI.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), token);
    }
}
