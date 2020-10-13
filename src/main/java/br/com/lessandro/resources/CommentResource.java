package br.com.lessandro.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lessandro.dto.CommentDto;
import br.com.lessandro.dto.PageDto;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.CurrentUser;
import br.com.lessandro.security.UserPrincipal;
import br.com.lessandro.service.ICommentService;

@RestController
@RequestMapping("/rest/comments")
public class CommentResource {

	@Autowired
	private ICommentService commentService;

	@GetMapping(path = "/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCommentsByPost(@PathVariable(name = "postId") String postId,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
		try {
			PageDto<CommentDto> comments = commentService.getAllComments(postId, page, size);
			return new ResponseEntity<>(comments, HttpStatus.OK);
		} catch (ValidationException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		}
	}

	@PostMapping(path = "/addComment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> addComment(@Valid @RequestBody CommentDto commentDto,
			@CurrentUser UserPrincipal currentUser) {
		try {
			return commentService.addComment(commentDto, currentUser);
		} catch (ValidationException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		}
	}

	@DeleteMapping(path = "/deleteComment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteComment(@PathVariable(name = "id") String commentId,
			@CurrentUser UserPrincipal currentUser) {
		try {
			return commentService.deleteComment(commentId, currentUser);
		} catch (ValidationException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		}
	}

}
