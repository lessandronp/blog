package br.com.lessandro.service.impl;

import java.util.Arrays;
import java.util.Collections;
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

import br.com.lessandro.dto.PageDto;
import br.com.lessandro.dto.PostDto;
import br.com.lessandro.model.Image;
import br.com.lessandro.model.Post;
import br.com.lessandro.model.User;
import br.com.lessandro.repository.PostRepository;
import br.com.lessandro.repository.UserRepository;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.UserPrincipal;
import br.com.lessandro.service.IPostService;
import br.com.lessandro.validator.PageValidator;

@Service
public class PostService implements IPostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private ModelMapper modelMapper;

	@Override
	public PageDto<PostDto> getAllPosts(int page, int size) {
		PageValidator.validatePageSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "creationDate");

		Page<Post> posts = postRepository.findAll(pageable);

		if (posts.getNumberOfElements() == 0) {
			return new PageDto<>(Collections.emptyList(), posts.getNumber(), posts.getSize(),
					posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
		}
		List<PostDto> postsDto = Arrays.asList(modelMapper.map(posts.getContent(), PostDto[].class));
		return new PageDto<>(postsDto, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
				posts.getTotalPages(), posts.isLast());
	}

	@Override
	public ResponseEntity<PostDto> addPost(PostDto postDto, UserPrincipal currentUser) {
		User user = userRepository.getUser(currentUser);
		postDto.setUser(user);
		Post post = modelMapper.map(postDto, Post.class);
		for (Image image : post.getImages()) {
			image.setPost(post);	
		}
		post = postRepository.save(post);
		postDto = modelMapper.map(post, PostDto.class);
		return new ResponseEntity<>(postDto, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> deletePost(Long id, UserPrincipal currentUser) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ValidationException("Post", "id",
				"Erro durante remoção do post ".concat(String.valueOf(id))));
		User user = userRepository.getUser(currentUser);
		if (post.getUser().getId().equals(user.getId())) {
			postRepository.deleteById(id);
			return new ResponseEntity<>("O post foi removido com sucesso.", HttpStatus.OK);
		}
		throw new ValidationException(System.currentTimeMillis(), HttpStatus.UNAUTHORIZED, "Permissão",
				"Você não tem permissão para a remoção.");
	}

}
