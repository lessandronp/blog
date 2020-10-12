package br.com.lessandro.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UserDto {
	
	private Long id;
	private String name;
	private String email;
	private String username;
	private String password;

	public UserDto(Long id, String email, String username, String name) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.name = name;
	}

}
