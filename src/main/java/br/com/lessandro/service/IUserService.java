package br.com.lessandro.service;

import org.springframework.security.core.userdetails.UserDetails;

import br.com.lessandro.dto.PageDto;
import br.com.lessandro.dto.UserDto;
import br.com.lessandro.security.UserPrincipal;

public interface IUserService {

	PageDto<UserDto> getAllUsers(int page, int size);
	UserDto getCurrentUser(UserPrincipal currentUser);
	UserDto addUser(UserDto user);
	UserDetails loadUserById(Long id);
}