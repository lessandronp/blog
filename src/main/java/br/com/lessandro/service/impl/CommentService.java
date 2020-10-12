package br.com.lessandro.service.impl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	public PageDto<CommentDto> getAllComments(Long postId, int page, int size) {
		PageValidator.validatePageSize(page, size);
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "creationDate");
		Page<Comment> comments = commentRepository.findByPostId(postId, pageable);
		List<CommentDto> commentsDto = Arrays.asList(modelMapper.map(comments.getContent(), CommentDto[].class));
		return new PageDto<>(commentsDto, comments.getNumber(), comments.getSize(),
				comments.getTotalElements(), comments.getTotalPages(), comments.isLast());
	}

	@Override
	public ResponseEntity<CommentDto> addComment(CommentDto commentDto, UserPrincipal currentUser) {
		Post post = postRepository.findById(commentDto.getIdPost()).orElseThrow(() -> new ValidationException("Post",
				"id", "O post com ID ".concat(String.valueOf(commentDto.getIdPost()).concat(" foi encontrado."))));
		User user = userRepository.getUser(currentUser);
		commentDto.setUser(user);
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment = commentRepository.save(comment);
		CommentDto savedCommentDto = modelMapper.map(comment, CommentDto.class);
		return new ResponseEntity<>(savedCommentDto, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> deleteComment(Long commentId, UserPrincipal currentUser) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ValidationException("Comment", "id",
				"Erro durante remoção do comentário ".concat(String.valueOf(commentId))));
		User user = userRepository.getUser(currentUser);
		if (comment.getUser().getId().equals(user.getId())) {
			commentRepository.deleteById(commentId);
			return new ResponseEntity<>("O comentário foi removido com sucesso.", HttpStatus.OK);
		}
		throw new ValidationException(System.currentTimeMillis(), HttpStatus.UNAUTHORIZED, "Permissão",
				"Você não tem permissão para a remoção.");
	}		
}
