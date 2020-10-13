package br.com.lessandro.service;

import org.springframework.http.ResponseEntity;

import br.com.lessandro.dto.PageDto;
import br.com.lessandro.dto.PostDto;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.UserPrincipal;

public interface IPostService {

	PageDto<PostDto> getAllPosts(int page, int size) throws ValidationException;

	ResponseEntity<PostDto> addPost(PostDto postDto, UserPrincipal currentUser) throws ValidationException;

	ResponseEntity<?> deletePost(String id, UserPrincipal currentUser) throws ValidationException;

}