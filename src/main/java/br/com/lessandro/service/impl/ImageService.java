package br.com.lessandro.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.lessandro.dto.ImageDto;
import br.com.lessandro.model.Image;
import br.com.lessandro.repository.ImageRepository;
import br.com.lessandro.resources.exception.ValidationException;
import br.com.lessandro.service.IImageService;
import br.com.lessandro.util.FileUtil;

@Service
public class ImageService implements IImageService {

	private static String BASE_PATH = FileUtils.getUserDirectoryPath();

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	ServletService servletService;

	@Override
	public void generateImageDisk(Long idImage, String filename, byte[] content) throws IOException {
		File fullPath = new File(BASE_PATH.concat("/images/").concat(String.valueOf(idImage)));
		FileUtil.generateFile(content, fullPath, filename);
	}

	@Override
	public byte[] convertBase64ToByteArray(String fileContent) throws UnsupportedEncodingException {
		if (StringUtils.isNotEmpty(fileContent)) {
			byte[] content = FileUtil.base64ToByteArray(fileContent);
			return content;
		}
		return null;
	}

	@Override
	public ImageDto loadImage(String idImage) throws IOException, ValidationException {
		if (StringUtils.isNotEmpty(idImage)) {
			Long id = Long.valueOf(idImage);
			Optional<Image> opImage = imageRepository.findById(id);
			if (opImage.isPresent()) {
				Image image = opImage.get();
				File fullPath = new File(
						BASE_PATH.concat("/images/").concat(idImage).concat("/").concat(image.getName()));
				if (fullPath.exists()) {
					byte[] contentByte = Files.readAllBytes(Paths.get(fullPath.toURI()));
					ImageDto imageDto = new ImageDto(id, image.getName(), contentByte, image.getUrl());
					return imageDto;
				}
				throw new ValidationException("A imagem não foi encontrada.");
			}
			throw new ValidationException("O identificador da imagem não foi encontrado.");
		}
		throw new ValidationException("O identificador da imagem deve ser informado.");
	}

	@Override
	public void generateImagesDisk(List<Image> images) throws ValidationException {
		for (Image image : images) {
			try {
				if (StringUtils.isNotEmpty(image.getContentFile())) {
					byte[] fileContent = this.convertBase64ToByteArray(image.getContentFile());
					String extension = FileUtil.getExtensionFromContent(fileContent);
					String filename = image.getName().concat(".").concat(extension);
					this.generateImageDisk(image.getId(), filename, fileContent);
					String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/")
							.path(String.valueOf(image.getId())).toUriString();
					image.setUrl(url);
					image.setContentFile(null);
					image.setName(filename);
				}
			} catch (IOException | TikaException e) {
				throw new ValidationException(e.getMessage());
			}
		}
	}

}
