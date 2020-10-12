package br.com.lessandro.util;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiOperation;

public class NumberUtil {


	@ApiOperation(value = "Formata o cpf com máscara")
	public static String formataCpf(String cpf) {
		if (StringUtils.isNotEmpty(cpf)) {
			return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." +
					cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
		}
		return null;
	}
}
