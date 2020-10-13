package br.com.lessandro.unit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.lessandro.dto.CommentDto;
import br.com.lessandro.dto.PageDto;
import br.com.lessandro.dto.PostDto;
import br.com.lessandro.dto.UserDto;
import br.com.lessandro.model.Comment;
import br.com.lessandro.model.Post;
import br.com.lessandro.model.User;
import br.com.lessandro.repository.CommentRepository;
import br.com.lessandro.repository.PostRepository;
import br.com.lessandro.repository.UserRepository;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.UserPrincipal;
import br.com.lessandro.service.ICommentService;
import br.com.lessandro.service.IPostService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentTest {

	private static String commentId;
	private static String postId;
	private static UserPrincipal currentUser;

	@Autowired
	ICommentService commentService;

	@Autowired
	IPostService postService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Test
	@Order(1)
	public void testAddComment() {
		try {
			currentUser = prepareUserAndRoles();
			savePostDto();
			CommentDto commentDto = prepareCommentDto();
			ResponseEntity<CommentDto> response = commentService.addComment(commentDto, currentUser);
			commentId = String.valueOf(response.getBody().getId());
			assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
			assertNotNull(commentId);
		} catch (ValidationException e) {
			Assertions.fail(e.getMessage());
		}
	}

	private void savePostDto() throws ValidationException {
		UserDto userDto = modelMapper.map(currentUser, UserDto.class);
		PostDto postDto = new PostDto(null, "Post teste", "Post teste content", userDto, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>());
		ResponseEntity<PostDto> response = postService.addPost(postDto, currentUser);
		postId = String.valueOf(response.getBody().getId());
	}

	private CommentDto prepareCommentDto() {
		UserDto userDto = modelMapper.map(currentUser, UserDto.class);
		CommentDto commentDto = new CommentDto(null, "Comment Teste", userDto, Long.parseLong(postId));
		return commentDto;
	}

	private UserPrincipal prepareUserAndRoles() {
		Optional<User> opUser = userRepository.findByUsername("user.teste");
		if (opUser.isPresent()) {
			UserPrincipal currentUser = UserPrincipal.create(opUser.get());
			return currentUser;
		}
		return null;
	}

	@Test
	@Order(2)
	public void testGetComments() {
		try {
			PageDto<CommentDto> page = commentService.getAllComments(postId, 0, 10);
			assertTrue(page.getContent().size() > 0);
		} catch (ValidationException e) {
			Assertions.fail(e.getMessage());
		}
	}

	@Test
	@Order(3)
	public void testDeleteComment() {
		try {
			ResponseEntity<?> response = commentService.deleteComment(commentId, currentUser);
			Optional<Comment> opComment = commentRepository.findById(Long.parseLong(commentId));
			assertTrue(!opComment.isPresent());
			assertTrue(response.getStatusCode().equals(HttpStatus.OK));
			deleteCommentPost();
		} catch (ValidationException e) {
			Assertions.fail(e.getMessage());
		}
	}

	private void deleteCommentPost() throws ValidationException {
		ResponseEntity<?> response = postService.deletePost(postId, currentUser);
		Optional<Post> opPost = postRepository.findById(Long.parseLong(postId));
		assertTrue(!opPost.isPresent());
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
	}
}
