package br.com.lessandro.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import br.com.lessandro.security.CurrentUser;
import br.com.lessandro.security.UserPrincipal;
import br.com.lessandro.service.ICommentService;

@RestController
@RequestMapping("/rest/comments")
public class CommentResource {
	
	@Autowired
	private ICommentService commentService;

	@GetMapping
	public ResponseEntity<PageDto<CommentDto>> getCommentsByPost(
			@PathVariable(name = "postId") Long postId,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
		PageDto<CommentDto> comments = commentService.getAllComments(postId, page, size);
		return new ResponseEntity< >(comments, HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<CommentDto> addComment(@Valid @RequestBody CommentDto commentDto,
			@CurrentUser UserPrincipal currentUser) {
		ResponseEntity<CommentDto> postDtoResponse = commentService.addComment(commentDto, currentUser);
		return postDtoResponse;
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteComment(@PathVariable(name = "id") Long commentId, 
			@CurrentUser UserPrincipal currentUser) {
		ResponseEntity<?> response = commentService.deleteComment(commentId, currentUser);
		return response;
	}

}
