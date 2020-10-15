package br.com.lessandro.validator;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import br.com.lessandro.dto.ImageDto;
import br.com.lessandro.resources.exception.ValidationException;

public class ImageValidator {

	public static void validateImage(ImageDto imageDto) throws ValidationException {
		if (StringUtils.isEmpty(imageDto.getContentFile()) && StringUtils.isEmpty(imageDto.getUrl())) {
			throw new ValidationException(System.currentTimeMillis(), HttpStatus.BAD_REQUEST, "Erro de validação",
					"O conteúdo da imagem na base64 ou a url deve ser informado.");
		}
	}
}
