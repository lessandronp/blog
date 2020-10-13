package br.com.lessandro.repository;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import br.com.lessandro.model.User;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.UserPrincipal;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(@NotBlank String username);

	Boolean existsByUsername(@NotBlank String username);

	Optional<User> findByUsernameOrEmail(String username, String email);

	default User getUser(UserPrincipal currentUser) throws ValidationException {
		return getUserByName(currentUser.getUsername());
	}

	default User getUserByName(String username) throws ValidationException {
		return findByUsername(username).orElseThrow(() -> new ValidationException("User", "username",
				HttpStatus.NOT_FOUND, username.concat(" não existe")));
	}

}
