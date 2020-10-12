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

import br.com.lessandro.dto.PageDto;
import br.com.lessandro.dto.PostDto;
import br.com.lessandro.security.CurrentUser;
import br.com.lessandro.security.UserPrincipal;
import br.com.lessandro.service.IPostService;

@RestController
@RequestMapping("/rest/posts")
public class PostResource {

	@Autowired
	private IPostService postService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PageDto<PostDto>> getPosts(
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
		PageDto<PostDto> posts = postService.getAllPosts(page, size);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@PostMapping(path = "/addPost", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<PostDto> addPost(@Valid @RequestBody PostDto postDto,
			@CurrentUser UserPrincipal currentUser) {
		ResponseEntity<PostDto> postDtoResponse = postService.addPost(postDto, currentUser);
		return postDtoResponse;
	}

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deletePost(@PathVariable(name = "id") Long postId,
			@CurrentUser UserPrincipal currentUser) {
		ResponseEntity<?> response = postService.deletePost(postId, currentUser);
		return response;
	}

}
