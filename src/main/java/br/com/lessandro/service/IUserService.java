package br.com.lessandro.service;

import br.com.lessandro.dto.UserDto;
import br.com.lessandro.security.UserPrincipal;

public interface IUserService {

	UserDto getCurrentUser(UserPrincipal currentUser);
	UserDto addUser(UserDto user);
}