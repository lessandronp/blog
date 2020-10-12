package br.com.lessandro.service;

import org.springframework.http.ResponseEntity;

import br.com.lessandro.dto.AlbumDto;
import br.com.lessandro.dto.PageDto;
import br.com.lessandro.security.UserPrincipal;

public interface IAlbumService {

	PageDto<AlbumDto> getAllAlbums(int page, int size);
	ResponseEntity<AlbumDto> addAlbum(AlbumDto albumDto, UserPrincipal currentUser);
	ResponseEntity<?> deleteAlbum(Long id, UserPrincipal currentUser);

}