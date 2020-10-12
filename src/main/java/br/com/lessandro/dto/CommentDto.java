package br.com.lessandro.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.lessandro.model.User;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CommentDto {
	
	private Long id;
	private String text;
	private User user;

}
