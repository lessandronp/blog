package br.com.lessandro.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import br.com.lessandro.resources.exception.ValidationException;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "Classe utilitária para a conversão da datas")
public class DataUtil {

	private static final String pattern = "dd/MM/yyyy";

	/**
	 * Formata a data com a máscara
	 * 
	 * @param data Data
	 * @return Data formatada
	 * @throws ValidationException
	 */
	public static String converteDataHoraString(Date data) throws ValidationException {
		try {
			if (data != null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
				LocalDateTime dateTime = LocalDateTime.ofInstant(data.toInstant(), ZoneId.systemDefault());
				return formatter.format(dateTime);
			}
		} catch (Exception e) {
			throw new ValidationException(e.getMessage());
		}
		return "";
	}

	/**
	 * Formata a string de data em Date
	 * 
	 * @param dataStr Data
	 * @return Data formatada
	 * @throws ValidationException
	 */
	public static Date converteStringDataHora(String dataStr) throws ValidationException {
		try {
			if (StringUtils.isNotEmpty(dataStr)) {
				DateTimeFormatter formato = DateTimeFormatter.ofPattern(pattern);
				LocalDate localDate = LocalDate.parse(dataStr, formato);
				Date data = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
				return data;
			}
		} catch (Exception e) {
			throw new ValidationException(e.getMessage());
		}
		return null;
	}

	/**
	 * Converte date em localdate
	 * 
	 * @param data Data
	 * @return LocalDate
	 */
	public static LocalDate converteDateLocalDate(Date data) {
		return data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
