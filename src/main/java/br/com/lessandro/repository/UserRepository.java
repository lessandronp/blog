package br.com.lessandro.repository;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lessandro.model.User;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.UserPrincipal;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(@NotBlank String username);

	Boolean existsByUsername(@NotBlank String username);

	Optional<User> findByUsernameOrEmail(String username, String email);

	default User getUser(UserPrincipal currentUser) {
		return getUserByName(currentUser.getUsername());
	}

	default User getUserByName(String username) {
		return findByUsername(username)
				.orElseThrow(() -> new ValidationException("User", "username", username.concat(" não existe")));
	}

}
