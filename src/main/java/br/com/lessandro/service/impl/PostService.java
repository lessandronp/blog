package br.com.lessandro.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.lessandro.dto.ImageDto;
import br.com.lessandro.dto.PageDto;
import br.com.lessandro.dto.PostDto;
import br.com.lessandro.model.Comment;
import br.com.lessandro.model.Image;
import br.com.lessandro.model.Link;
import br.com.lessandro.model.Post;
import br.com.lessandro.model.User;
import br.com.lessandro.repository.PostRepository;
import br.com.lessandro.repository.UserRepository;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.UserPrincipal;
import br.com.lessandro.service.IImageService;
import br.com.lessandro.service.IPostService;
import br.com.lessandro.validator.ImageValidator;
import br.com.lessandro.validator.PageValidator;

@Service
public class PostService implements IPostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IImageService imageService;

	@Override
	public PageDto<PostDto> getAllPosts(int page, int size) throws ValidationException {
		PageValidator.validatePageSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "creationDate");

		Page<Post> posts = postRepository.findAll(pageable);

		if (posts.getNumberOfElements() == 0) {
			return new PageDto<>(Collections.emptyList(), posts.getNumber(), posts.getSize(), posts.getTotalElements(),
					posts.getTotalPages(), posts.isLast());
		}
		List<PostDto> postsDto = Arrays.asList(modelMapper.map(posts.getContent(), PostDto[].class));
		return new PageDto<>(postsDto, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
				posts.getTotalPages(), posts.isLast());
	}

	@Override
	public ResponseEntity<PostDto> addPost(PostDto postDto, UserPrincipal currentUser) throws ValidationException {
		validateImages(postDto);
		User user = userRepository.getUser(currentUser);
		Post post = modelMapper.map(postDto, Post.class);
		preparePostRelationship(post, user);
		post.setUser(user);
		post = postRepository.save(post);
		imageService.generateImagesDisk(post.getImages());
		post = postRepository.save(post);
		postDto = modelMapper.map(post, PostDto.class);
		return new ResponseEntity<>(postDto, HttpStatus.CREATED);
	}

	private void validateImages(PostDto postDto) throws ValidationException {
		for (ImageDto imageDto : postDto.getImages()) {
			ImageValidator.validateImage(imageDto);
		}
	}

	private void preparePostRelationship(Post post, User user) {
		if (post.getImages() != null) {
			for (Image image : post.getImages()) {
				image.setPost(post);
			}
		}
		if (post.getLinks() != null) {
			for (Link link : post.getLinks()) {
				link.setPost(post);
			}
		}
		if (post.getComments() != null) {
			for (Comment comment : post.getComments()) {
				comment.setPost(post);
				comment.setUser(user);
			}
		}
	}

	@Override
	public ResponseEntity<?> deletePost(String id, UserPrincipal currentUser) throws ValidationException {
		if (StringUtils.isNotEmpty(id)) {
			Long postId = Long.parseLong(id);
			String postNotFound = String.format("O Post informado não existe com esse ID: %s", id);
			Post post = postRepository.findById(postId)
					.orElseThrow(() -> new ValidationException("Post", "id", HttpStatus.NOT_FOUND, postNotFound));
			User user = userRepository.getUser(currentUser);
			if (post.getUser().getId().equals(user.getId())) {
				postRepository.deleteById(postId);
				return new ResponseEntity<>("O post foi removido com sucesso.", HttpStatus.OK);
			}
			throw new ValidationException(System.currentTimeMillis(), HttpStatus.UNAUTHORIZED, "Permissão",
					"Você não tem permissão para a remoção.");
		}
		throw new ValidationException(System.currentTimeMillis(), HttpStatus.BAD_REQUEST, "Parâmetro",
				"O parâmetro identificador do post precisa ser informado.");
	}

}
