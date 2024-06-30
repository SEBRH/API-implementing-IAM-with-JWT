package group.name.IamAPI.iam.interfaces.rest.transform;

import group.name.IamAPI.iam.domain.model.aggregates.User;
import group.name.IamAPI.iam.domain.model.entities.Role;
import group.name.IamAPI.iam.interfaces.rest.resources.UserResource;


/***
 * This class is responsible for converting a User entity to a UserResource.
 * It also converts the roles of the user to a list of strings.
 *
 */

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user){
        var roles = user.getRoles().stream()
                .map(Role::getStringName).toList();
        return new UserResource(user.getId(), user.getUsername(), roles);
    }
}
