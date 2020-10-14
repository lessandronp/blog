package br.com.lessandro.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lessandro.dto.JwtAuthenticationDto;
import br.com.lessandro.dto.PageDto;
import br.com.lessandro.dto.UserDto;
import br.com.lessandro.dto.UserCredentialDto;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.CurrentUser;
import br.com.lessandro.security.JwtTokenProvider;
import br.com.lessandro.security.UserPrincipal;
import br.com.lessandro.service.IUserService;

@RestController
@RequestMapping("/rest/users")
public class UserResource {

	@Autowired
	private IUserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllUsers(
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
		try {
			PageDto<UserDto> users = userService.getAllUsers(page, size);
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (ValidationException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		}
	}

	@GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserDto> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		UserDto userDto = userService.getCurrentUser(currentUser);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

	@PostMapping(path = "/addUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> addUser(@Valid @RequestBody UserCredentialDto user) {
		try {
			UserDto newUser = userService.addUser(user);
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		} catch (ValidationException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		}
	}

	@PostMapping(path = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JwtAuthenticationDto> authenticateUser(@Valid @RequestBody UserCredentialDto user) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationDto(jwt));
	}
}
