package br.com.lessandro.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.lessandro.dto.AlbumDto;
import br.com.lessandro.dto.PageDto;
import br.com.lessandro.dto.mapper.AlbumMapper;
import br.com.lessandro.model.Album;
import br.com.lessandro.model.User;
import br.com.lessandro.repository.AlbumRepository;
import br.com.lessandro.repository.UserRepository;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.UserPrincipal;
import br.com.lessandro.service.IAlbumService;
import br.com.lessandro.validator.PageValidator;

@Service
public class AlbumService implements IAlbumService {

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public PageDto<AlbumDto> getAllAlbums(int page, int size) {
		PageValidator.validatePageSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "creationDate");

		Page<Album> albuns = albumRepository.findAll(pageable);

		if (albuns.getNumberOfElements() == 0) {
			return new PageDto<>(Collections.emptyList(), albuns.getNumber(), albuns.getSize(),
					albuns.getTotalElements(), albuns.getTotalPages(), albuns.isLast());
		}
		List<AlbumDto> albunsDto = AlbumMapper.INSTANCE.entitiesToDtos(albuns.getContent());
		return new PageDto<>(albunsDto, albuns.getNumber(), albuns.getSize(), albuns.getTotalElements(),
				albuns.getTotalPages(), albuns.isLast());
	}

	@Override
	public ResponseEntity<AlbumDto> addAlbum(AlbumDto albumDto, UserPrincipal currentUser) {
		User user = userRepository.getUser(currentUser);
		Album album = new Album();
		album.setUser(user);
		album = AlbumMapper.INSTANCE.dtoToEntity(albumDto);
		Album newAlbum = albumRepository.save(album);
		albumDto = AlbumMapper.INSTANCE.entityToDto(newAlbum);
		return new ResponseEntity<>(albumDto, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> deleteAlbum(Long id, UserPrincipal currentUser) {
		Album album = albumRepository.findById(id).orElseThrow(() -> new ValidationException("Album", "id",
				"Erro durante remoção do álbum ".concat(String.valueOf(id))));
		User user = userRepository.getUser(currentUser);
		if (album.getUser().getId().equals(user.getId())) {
			albumRepository.deleteById(id);
			return new ResponseEntity<>("O álbum foi removido com sucesso.", HttpStatus.OK);
		}
		throw new ValidationException(System.currentTimeMillis(), HttpStatus.UNAUTHORIZED, "Permissão",
				"Você não tem permissão para a remoção.");
	}

}
