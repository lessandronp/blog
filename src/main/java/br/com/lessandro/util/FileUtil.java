package br.com.lessandro.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;

public class FileUtil {

	public static String generateFile(byte[] bytes, File directory, String filename) throws IOException {
		try {
			if (!directory.exists()) {
				directory.mkdirs();
			}

			File arquivoCompleto = new File(directory, filename);

			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			FileOutputStream out = new FileOutputStream(arquivoCompleto);

			IOUtils.copy(in, out);

			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);

			return arquivoCompleto.getName();
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}
	}

	public static String generateDynamicFilename(String extension) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(date).concat(".").concat(extension);
	}

	public static byte[] base64ToByteArray(String content) throws UnsupportedEncodingException {
		String partSeparator = ",";
		if (content.contains(partSeparator)) {
			content = content.split(partSeparator)[1].trim();
			byte[] decodedString = Base64.getDecoder().decode(new String(content).getBytes("UTF-8"));
			return decodedString;
		}
		return null;
	}

	public static String getExtensionFromContent(byte[] contentBase64)
			throws IOException, TikaException {
		TikaConfig tikaConfig = new TikaConfig();
		Detector detector = tikaConfig.getDetector();
		TikaInputStream stream = TikaInputStream.get(contentBase64);
		Metadata metadata = new Metadata();
		MediaType mediaType = detector.detect(stream, metadata);
		MimeType mimeType = tikaConfig.getMimeRepository().forName(mediaType.toString());
		String extension = mimeType.getExtension().split("\\.")[1];
		return extension;
	}
	
	public static String getResourcePath(String resourceFolder) throws IOException {
		return new ClassPathResource(resourceFolder).getFile().getAbsolutePath();
	}
}
