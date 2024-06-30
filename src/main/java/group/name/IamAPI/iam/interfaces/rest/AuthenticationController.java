package group.name.IamAPI.iam.interfaces.rest;


import group.name.IamAPI.iam.domain.services.UserCommandService;
import group.name.IamAPI.iam.interfaces.rest.resources.AuthenticatedUserResource;
import group.name.IamAPI.iam.interfaces.rest.resources.SignInResource;
import group.name.IamAPI.iam.interfaces.rest.resources.SignUpResource;
import group.name.IamAPI.iam.interfaces.rest.resources.UserResource;
import group.name.IamAPI.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import group.name.IamAPI.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import group.name.IamAPI.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import group.name.IamAPI.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {
    private final UserCommandService userCommandService;

    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    /**
     * Handles the sign-in request.
     * It creates the command from the request body and delegates the command to the user command service.
     * If the user is not found, it returns a 404 response.
     * It then creates the authenticated user resource from the user and the token.
     * It then returns the authenticated user resource.
     * @param signInResource the sign-in request body.
     * @return the authenticated user resource.
     * @author SebastianRamirez
     */

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource signInResource){
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
        var authenticatedUser = userCommandService.handle(signInCommand);
        if (authenticatedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());
        return ResponseEntity.ok(authenticatedUserResource);
    }

    /**
     * Handles the sign-up request.
     * @param signUpResource the sign-up request body.
     * @return the created user resource.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var user = userCommandService.handle(signUpCommand);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);

    }




}
