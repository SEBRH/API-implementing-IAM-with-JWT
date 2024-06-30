package group.name.IamAPI.iam.domain.model.commands;

import group.name.IamAPI.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String username, String password, List<String> roles) {
}
