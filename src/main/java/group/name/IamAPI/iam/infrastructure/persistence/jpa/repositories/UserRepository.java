package group.name.IamAPI.iam.infrastructure.persistence.jpa.repositories;

import group.name.IamAPI.iam.domain.model.aggregates.User;
import group.name.IamAPI.iam.domain.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    List<User> getAllByRoles(Set<Role> roles);
}
