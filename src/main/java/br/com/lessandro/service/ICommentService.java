package br.com.lessandro.service;

import org.springframework.http.ResponseEntity;

import br.com.lessandro.dto.CommentDto;
import br.com.lessandro.dto.PageDto;
import br.com.lessandro.security.UserPrincipal;

public interface ICommentService {

	PageDto<CommentDto> getAllComments(Long idPost, int page, int size);
	ResponseEntity<CommentDto> addComment(CommentDto commentDto, UserPrincipal currentUser);
	ResponseEntity<?> deleteComment(Long id, UserPrincipal currentUser);

}