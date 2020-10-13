package br.com.lessandro.service;

import org.springframework.http.ResponseEntity;

import br.com.lessandro.dto.AlbumDto;
import br.com.lessandro.dto.PageDto;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.UserPrincipal;

public interface IAlbumService {

	PageDto<AlbumDto> getAllAlbums(int page, int size) throws ValidationException;
	ResponseEntity<AlbumDto> addAlbum(AlbumDto albumDto, UserPrincipal currentUser) throws ValidationException;
	ResponseEntity<?> deleteAlbum(String id, UserPrincipal currentUser) throws ValidationException;

}