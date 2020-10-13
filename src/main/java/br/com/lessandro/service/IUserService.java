package br.com.lessandro.service;

import org.springframework.security.core.userdetails.UserDetails;

import br.com.lessandro.dto.PageDto;
import br.com.lessandro.dto.UserDto;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.UserPrincipal;

public interface IUserService {

	PageDto<UserDto> getAllUsers(int page, int size) throws ValidationException;

	UserDto getCurrentUser(UserPrincipal currentUser);

	UserDto addUser(UserDto user) throws ValidationException;

	UserDetails loadUserById(Long id);
}