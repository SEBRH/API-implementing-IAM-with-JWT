package group.name.IamAPI.iam.interfaces.rest.transform;

import group.name.IamAPI.iam.domain.model.entities.Role;
import group.name.IamAPI.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role){
        return new RoleResource(role.getId(), role.getStringName());
    }
}
