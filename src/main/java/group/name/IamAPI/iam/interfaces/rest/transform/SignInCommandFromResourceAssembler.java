package group.name.IamAPI.iam.interfaces.rest.transform;

import group.name.IamAPI.iam.domain.model.commands.SignInCommand;
import group.name.IamAPI.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource){
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}
