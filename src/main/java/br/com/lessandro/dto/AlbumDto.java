package br.com.lessandro.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.lessandro.model.User;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class AlbumDto {
	
	private Long id;
	private String title;
	private User user;
	private List<ImageDto> image;
	public List<ImageDto> getImage() {

		return image == null ? null : new ArrayList<>(image);
	}

	public void setImage(List<ImageDto> image) {

		if (image == null) {
			this.image = null;
		} else {
			this.image = Collections.unmodifiableList(image);
		}
	}

}
