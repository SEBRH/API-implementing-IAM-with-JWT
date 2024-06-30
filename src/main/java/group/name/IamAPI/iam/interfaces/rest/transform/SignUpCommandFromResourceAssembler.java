package group.name.IamAPI.iam.interfaces.rest.transform;

import group.name.IamAPI.iam.domain.model.commands.SignUpCommand;
import group.name.IamAPI.iam.domain.model.entities.Role;
import group.name.IamAPI.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;

/***
 * This class is responsible for converting a SignUpResource object into a SignUpCommand object.
 * it also converts the roles of the user to a list of Role objects.
 */

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(resource.username(), resource.password(), resource.roles());
    }
}
