package br.com.lessandro.service;

import org.springframework.http.ResponseEntity;

import br.com.lessandro.dto.CommentDto;
import br.com.lessandro.dto.PageDto;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.UserPrincipal;

public interface ICommentService {

	PageDto<CommentDto> getAllComments(String idPost, int page, int size) throws ValidationException;

	ResponseEntity<CommentDto> addComment(CommentDto commentDto, UserPrincipal currentUser) throws ValidationException;

	ResponseEntity<?> deleteComment(String id, UserPrincipal currentUser) throws ValidationException;

}