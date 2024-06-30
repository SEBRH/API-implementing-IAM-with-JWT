package group.name.IamAPI.iam.domain.services;

import group.name.IamAPI.iam.domain.model.aggregates.User;
import group.name.IamAPI.iam.domain.model.commands.SignInCommand;
import group.name.IamAPI.iam.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;
//ImmutablePair is a class from the Apache Commons Lang library is used to hold a pair of objects. in this case
//it is used to hold the user and the token and since is immutable it is thread safe (cant change the values)

public interface UserCommandService {
Optional<User>handle(SignUpCommand command);
Optional<ImmutablePair<User, String>> handle(SignInCommand command);

}
