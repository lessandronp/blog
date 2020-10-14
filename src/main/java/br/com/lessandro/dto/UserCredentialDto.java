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
public class UserCredentialDto {
	
	private Long id;
	private String name;
	private String email;
	private String username;
	private String password;

	public UserCredentialDto(Long id, String email, String username, String name) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.name = name;
	}

}
