package br.com.lessandro.util;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import br.com.lessandro.resources.exception.ValidationException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;

@ApiModel(description = "Classe utilitária para a conversão da datas")
public class DataUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String pattern = "dd/MM/yyyy";

	/**
	 * Formata a data com a máscara
	 * @param data Data
	 * @return Data formatada
	 * @throws ValidationException
	 */
	@ApiOperation(value = "Método de conversão de data em string")
	public static String converteDataHoraString(Date data) throws ValidationException {
		try {
			if (data != null) {
				DateTime dateTime = new DateTime(data);
				return dateTime.toString(pattern);
			}
		} catch (Exception e) {
			throw new ValidationException(e.getMessage());
		}
		return "";
	}

	/**
	 * Formata a string de data em Date
	 * @param dataStr Data
	 * @return Data formatada
	 * @throws ValidationException
	 */
	@ApiOperation(value = "Método de conversão de data em string para date")
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
	 * Calcula a idade
	 * @param dataNascimento Data de nascimento
	 * @param dataAtual Data atual
	 * @return Idade
	 */
	@ApiOperation(value = "Calcula a idade")
	public static int calculaIdade(LocalDate dataNascimento, LocalDate dataAtual) {
		return Period.between(dataNascimento, dataAtual).getYears();
	}

	/**
	 * Converte date em localdate
	 * @param data Data
	 * @return LocalDate
	 */
	@ApiOperation(value = "Converte date em localdate")
	public static LocalDate converteDateLocalDate(Date data) {
		return data.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}
}
