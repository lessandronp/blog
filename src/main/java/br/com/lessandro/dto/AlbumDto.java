package br.com.lessandro.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {
	
	private Long id;
	private String name;
	private UserDto user;
	private List<ImageDto> images;
	
	public List<ImageDto> getImages() {
		return images == null ? null : new ArrayList<>(images);
	}

	public void setImages(List<ImageDto> images) {
		if (images == null) {
			this.images = null;
		} else {
			this.images = Collections.unmodifiableList(images);
		}
	}
}
