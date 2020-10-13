package br.com.lessandro.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.CurrentUser;
import br.com.lessandro.security.UserPrincipal;
import br.com.lessandro.service.IAlbumService;

@RestController
@RequestMapping("/rest/albums")
public class AlbumResource {

	@Autowired
	private IAlbumService albumService;

	@GetMapping
	public ResponseEntity<?> getAlbums(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
		try {
			PageDto<AlbumDto> albums = albumService.getAllAlbums(page, size);
			return new ResponseEntity<>(albums, HttpStatus.OK);
		} catch (ValidationException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		}
	}

	@PostMapping(path = "/addAlbum", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> addAlbum(@Valid @RequestBody AlbumDto albumDto, @CurrentUser UserPrincipal currentUser) {
		try {
			return albumService.addAlbum(albumDto, currentUser);
		} catch (ValidationException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		}
	}

	@DeleteMapping(path = "/deleteAlbum/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteAlbum(@PathVariable(name = "id") String albumId,
			@CurrentUser UserPrincipal currentUser) {
		try {
			return albumService.deleteAlbum(albumId, currentUser);
		} catch (ValidationException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		}
	}

}
