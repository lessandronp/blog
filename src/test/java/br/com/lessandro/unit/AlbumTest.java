package br.com.lessandro.unit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.lessandro.dto.AlbumDto;
import br.com.lessandro.dto.ImageDto;
import br.com.lessandro.dto.PageDto;
import br.com.lessandro.dto.UserDto;
import br.com.lessandro.model.Album;
import br.com.lessandro.model.User;
import br.com.lessandro.repository.AlbumRepository;
import br.com.lessandro.repository.UserRepository;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.security.UserPrincipal;
import br.com.lessandro.service.IAlbumService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlbumTest {

	private static String albumId;
	private static UserPrincipal currentUser;

	@Autowired
	IAlbumService albumService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AlbumRepository albumRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Test
	@Order(1)
	public void testAddAlbum() {
		try {
			currentUser = prepareUserAndRoles();
			AlbumDto albumDto = prepareAlbumDto();
			ResponseEntity<AlbumDto> response = albumService.addAlbum(albumDto, currentUser);
			albumId = String.valueOf(response.getBody().getId());
			assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
			assertNotNull(albumId);
		} catch (ValidationException e) {
			Assertions.fail(e.getMessage());
		}
	}

	private AlbumDto prepareAlbumDto() {
		UserDto userDto = modelMapper.map(currentUser, UserDto.class);
		List<ImageDto> images = new ArrayList<>();
		images.add(new ImageDto(null, "Image Test", "http://www.teste.com.br/img.png"));
		AlbumDto albumDto = new AlbumDto(null, "Álbum Teste", userDto, images);
		return albumDto;
	}

	private UserPrincipal prepareUserAndRoles() {
		Optional<User> opUser = userRepository.findByUsername("user.teste");
		if (opUser.isPresent()) {
			UserPrincipal currentUser = UserPrincipal.create(opUser.get());
			return currentUser;
		}
		return null;
	}

	@Test
	@Order(2)
	public void testGetAlbums() {
		try {
			PageDto<AlbumDto> page = albumService.getAllAlbums(0, 10);
			assertTrue(page.getContent().size() > 0);
		} catch (ValidationException e) {
			Assertions.fail(e.getMessage());
		}
	}

	@Test
	@Order(3)
	public void testDeleteAlbum() {
		try {
			ResponseEntity<?> response = albumService.deleteAlbum(albumId, currentUser);
			Optional<Album> opAlbum = albumRepository.findById(Long.parseLong(albumId));
			assertTrue(!opAlbum.isPresent());
			assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		} catch (ValidationException e) {
			Assertions.fail(e.getMessage());
		}
	}

}
