package br.com.lessandro.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.lessandro.dto.AlbumDto;
import br.com.lessandro.dto.PageDto;
import br.com.lessandro.dto.mapper.AlbumMapper;
import br.com.lessandro.model.Album;
import br.com.lessandro.repository.AlbumRepository;
import br.com.lessandro.security.UserPrincipal;
import br.com.lessandro.service.IAlbumService;
import br.com.lessandro.validator.PageValidator;

@Service
public class AlbumService implements IAlbumService {

	@Autowired
	private AlbumRepository albumRepository;

	@Override
	public PageDto<AlbumDto> getAllAlbums(int page, int size) {
		PageValidator.validatePageSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "title");

		Page<Album> albums = albumRepository.findAll(pageable);

		if (albums.getNumberOfElements() == 0) {
			return new PageDto<>(Collections.emptyList(), albums.getNumber(), albums.getSize(),
					albums.getTotalElements(), albums.getTotalPages(), albums.isLast());
		}
		List<AlbumDto> albumResponses = AlbumMapper.INSTANCE.entitiesToDtos(albums.getContent());
		return new PageDto<>(albumResponses, albums.getNumber(), albums.getSize(), albums.getTotalElements(),
				albums.getTotalPages(), albums.isLast());
	}

	@Override
	public ResponseEntity<AlbumDto> addAlbum(AlbumDto albumDto, UserPrincipal currentUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deleteAlbum(Long id, UserPrincipal currentUser) {
		// TODO Auto-generated method stub
		return null;
	}

}
