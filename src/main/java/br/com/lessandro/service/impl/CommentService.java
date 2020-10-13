package br.com.lessandro.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.lessandro.dto.CommentDto;
import br.com.lessandro.dto.PageDto;
import br.com.lessandro.model.Comment;
import br.com.lessandro.model.Post;
import br.com.lessandro.model.User;
import br.com.lessandro.repository.CommentRepository;
import br.com.lessandro.repository.PostRepository;
import br.com.lessandro.repository.UserRepository;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.UserPrincipal;
import br.com.lessandro.service.ICommentService;
import br.com.lessandro.validator.PageValidator;

@Service
public class CommentService implements ICommentService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PageDto<CommentDto> getAllComments(String postId, int page, int size) throws ValidationException {
		PageValidator.validatePageSize(page, size);
		Page<Comment> comments = new PageImpl<>(new ArrayList<>());
		if (StringUtils.isNotEmpty(postId)) {
			Long pId = Long.parseLong(postId);
			Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "creationDate");
			comments = commentRepository.findByPostId(pId, pageable);
		}
		List<CommentDto> commentsDto = Arrays.asList(modelMapper.map(comments.getContent(), CommentDto[].class));
		return new PageDto<>(commentsDto, comments.getNumber(), comments.getSize(), comments.getTotalElements(),
				comments.getTotalPages(), comments.isLast());
	}

	@Override
	public ResponseEntity<CommentDto> addComment(CommentDto commentDto, UserPrincipal currentUser)
			throws ValidationException {
		String postNotFound = String.format("O post com ID %s não foi encontrado.", commentDto.getIdPost());
		Post post = postRepository.findById(commentDto.getIdPost())
				.orElseThrow(() -> new ValidationException("Post", "id", HttpStatus.NOT_FOUND, postNotFound));
		User user = userRepository.getUser(currentUser);
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setUser(user);
		comment.setPost(post);
		comment = commentRepository.save(comment);
		CommentDto savedCommentDto = modelMapper.map(comment, CommentDto.class);
		return new ResponseEntity<>(savedCommentDto, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> deleteComment(String id, UserPrincipal currentUser) throws ValidationException {
		if (StringUtils.isNotEmpty(id)) {
			Long commentId = Long.parseLong(id);
			String commentNotFound = String.format("O comentário informado não existe com esse ID: %s", id);
			Comment comment = commentRepository.findById(commentId)
					.orElseThrow(() -> new ValidationException("Comment", "id", HttpStatus.NOT_FOUND, commentNotFound));
			User user = userRepository.getUser(currentUser);
			if (comment.getUser().getId().equals(user.getId())) {
				commentRepository.deleteById(commentId);
				return new ResponseEntity<>("O comentário foi removido com sucesso.", HttpStatus.OK);
			}
			throw new ValidationException(System.currentTimeMillis(), HttpStatus.UNAUTHORIZED, "Permissão",
					"Você não tem permissão para a remoção.");
		} else {
			throw new ValidationException(System.currentTimeMillis(), HttpStatus.BAD_REQUEST, "Parâmetro",
					"O parâmetro identificador do comentário precisa ser informado.");
		}
	}
}
