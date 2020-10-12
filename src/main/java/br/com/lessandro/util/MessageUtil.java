package br.com.lessandro.util;

import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por recuperar mensagens do arquivo de internacionalização.
 * 
 * @author Lessandro
 */
@Component
public class MessageUtil {

	@Autowired
	public MessageSource messageSource;

	/**
	 * Recupera a mensagem do arquivo de internacionalização.
	 * 
	 * @param key Chave a ser recuperada
	 * @param args Argumentos
	 * @return Mensagem
	 * @throws IOException
	 */
	public String getMessage(String key, Object... args) {
		String message = messageSource.getMessage(key, args, new Locale("pt", "BR"));
		return message;
	}

}
