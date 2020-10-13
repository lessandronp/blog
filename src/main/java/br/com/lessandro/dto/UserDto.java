package br.com.lessandro.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
public class UserDto {
	
	private Long id;
	private String name;
	private String email;

	@JsonIgnore
	@JsonProperty(access = Access.WRITE_ONLY)
	private String username;
	
	@JsonIgnore
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	public UserDto(Long id, String email, String username, String name) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.name = name;
	}

}
