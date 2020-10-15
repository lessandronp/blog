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

import br.com.lessandro.dto.AlbumDto;
import br.com.lessandro.dto.ImageDto;
import br.com.lessandro.dto.PageDto;
import br.com.lessandro.model.Album;
import br.com.lessandro.model.Image;
import br.com.lessandro.model.User;
import br.com.lessandro.repository.AlbumRepository;
import br.com.lessandro.repository.UserRepository;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.UserPrincipal;
import br.com.lessandro.service.IAlbumService;
import br.com.lessandro.service.IImageService;
import br.com.lessandro.validator.ImageValidator;
import br.com.lessandro.validator.PageValidator;

@Service
public class AlbumService implements IAlbumService {

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IImageService imageService;

	@Override
	public PageDto<AlbumDto> getAllAlbums(int page, int size) throws ValidationException {
		PageValidator.validatePageSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "creationDate");
		Page<Album> albuns = albumRepository.findAll(pageable);

		if (albuns.getNumberOfElements() == 0) {
			return new PageDto<>(Collections.emptyList(), albuns.getNumber(), albuns.getSize(),
					albuns.getTotalElements(), albuns.getTotalPages(), albuns.isLast());
		}
		List<AlbumDto> albunsDto = Arrays.asList(modelMapper.map(albuns.getContent(), AlbumDto[].class));
		return new PageDto<>(albunsDto, albuns.getNumber(), albuns.getSize(), albuns.getTotalElements(),
				albuns.getTotalPages(), albuns.isLast());
	}

	@Override
	public ResponseEntity<AlbumDto> addAlbum(AlbumDto albumDto, UserPrincipal currentUser) throws ValidationException {
		validateImages(albumDto);
		User user = userRepository.getUser(currentUser);
		Album album = modelMapper.map(albumDto, Album.class);
		album.setUser(user);
		prepareAlbumRelationship(album);
		album = albumRepository.save(album);
		imageService.generateImagesDisk(album.getImages());
		album = albumRepository.save(album);
		albumDto = modelMapper.map(album, AlbumDto.class);
		return new ResponseEntity<>(albumDto, HttpStatus.CREATED);
	}

	private void validateImages(AlbumDto albumDto) throws ValidationException {
		for (ImageDto imageDto : albumDto.getImages()) {
			ImageValidator.validateImage(imageDto);
		}
	}

	private void prepareAlbumRelationship(Album album) {
		if (album.getImages() != null) {
			for (Image image : album.getImages()) {
				image.setAlbum(album);
			}
		}
	}

	@Override
	public ResponseEntity<?> deleteAlbum(String id, UserPrincipal currentUser) throws ValidationException {
		if (StringUtils.isNotEmpty(id)) {
			Long idAlbum = Long.valueOf(id);
			String albumNotFound = String.format("O álbum informado não existe com esse ID: %s", id);
			Album album = albumRepository.findById(idAlbum)
					.orElseThrow(() -> new ValidationException("Album", "id", HttpStatus.NOT_FOUND, albumNotFound));
			User user = userRepository.getUser(currentUser);
			if (album.getUser().getId().equals(user.getId())) {
				albumRepository.deleteById(idAlbum);
				return new ResponseEntity<>("O álbum foi removido com sucesso.", HttpStatus.OK);
			}
			throw new ValidationException(System.currentTimeMillis(), HttpStatus.UNAUTHORIZED, "Permissão",
					"Você não tem permissão para a remoção.");
		}
		throw new ValidationException(System.currentTimeMillis(), HttpStatus.BAD_REQUEST, "Parâmetro",
				"O parâmetro identificador do álbum precisa ser informado.");
	}

}
