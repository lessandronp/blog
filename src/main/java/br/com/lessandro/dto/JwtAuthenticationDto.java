package br.com.lessandro.dto;

import lombok.Data;

@Data
public class JwtAuthenticationDto {

	private String accessToken;
	private String type = "Bearer";

	public JwtAuthenticationDto(String accessToken) {
		this.accessToken = accessToken;
	}
}
