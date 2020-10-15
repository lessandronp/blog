package br.com.lessandro.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

	private Long id;
	private String name;
	private String contentFile;
	private String url;
	private String extensionFile;
	private byte[] contentByte;

	public ImageDto(Long id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}

	public ImageDto(Long id, String name, byte[] contentByte, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.contentByte = contentByte;
	}

}
