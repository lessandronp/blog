package br.com.lessandro.service;

import org.springframework.http.ResponseEntity;

import br.com.lessandro.dto.PageDto;
import br.com.lessandro.dto.PostDto;
import br.com.lessandro.security.UserPrincipal;

public interface IPostService {

	PageDto<PostDto> getAllPosts(int page, int size);
	ResponseEntity<PostDto> addPost(PostDto postDto, UserPrincipal currentUser);
	ResponseEntity<?> deletePost(Long id, UserPrincipal currentUser);

}