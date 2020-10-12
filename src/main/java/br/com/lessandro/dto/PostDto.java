package br.com.lessandro.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.lessandro.model.User;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PostDto {
	
	private Long id;
	private String title;
	private String body;
	private User user;
	private List<CommentDto> comments;
	private List<ImageDto> images;
	private List<LinkDto> links;
}