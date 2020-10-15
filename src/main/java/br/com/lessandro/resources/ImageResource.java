package br.com.lessandro.resources;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lessandro.dto.ImageDto;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.service.IImageService;

@RestController
@RequestMapping("/images/")
public class ImageResource {

	@Autowired
	private IImageService imageService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getFile(@PathVariable String id) {
		try {
			ImageDto imageDto = imageService.loadImage(id);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageDto.getName() + "\"")
					.body(imageDto.getContentByte());
		} catch (ValidationException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
