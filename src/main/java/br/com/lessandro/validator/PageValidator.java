package br.com.lessandro.validator;

import org.springframework.http.HttpStatus;

import br.com.lessandro.resources.exception.ValidationException;

public class PageValidator {

	public static void validatePageSize(int page, int size) {
		if (page < 0) {
			throw new ValidationException(System.currentTimeMillis(), HttpStatus.BAD_REQUEST, "Erro de validação",
					"A página não pode ser menor que zero");
		}

		if (size < 0) {
			throw new ValidationException(System.currentTimeMillis(), HttpStatus.BAD_REQUEST, "Erro de validação",
					"O tamanho de páginas não pode ser menor que zero");
		}

		if (size > 10) {
			throw new ValidationException(System.currentTimeMillis(), HttpStatus.BAD_REQUEST, "Erro de validação",
					"O tamanho de páginas não pode ser maior que 10");
		}
	}

}
