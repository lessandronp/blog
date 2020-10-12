package br.com.lessandro.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lessandro.dto.AlbumDto;
import br.com.lessandro.dto.PageDto;
import br.com.lessandro.security.CurrentUser;
import br.com.lessandro.security.UserPrincipal;
import br.com.lessandro.service.IAlbumService;

@RestController
@RequestMapping("/rest/albums")
public class AlbumResource {
	
	@Autowired
	private IAlbumService albumService;

	@GetMapping
	public ResponseEntity<PageDto<AlbumDto>> getAlbums(
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
		PageDto<AlbumDto> albums = albumService.getAllAlbums(page, size);
		return new ResponseEntity<>(albums, HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<AlbumDto> addAlbum(@Valid @RequestBody AlbumDto albumDto,
			@CurrentUser UserPrincipal currentUser) {
		ResponseEntity<AlbumDto> albumResponse = albumService.addAlbum(albumDto, currentUser);
		return albumResponse;
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteAlbum(@PathVariable(name = "id") Long albumId, 
			@CurrentUser UserPrincipal currentUser) {
		ResponseEntity<?> response = albumService.deleteAlbum(albumId, currentUser);
		return response;
	}

}
