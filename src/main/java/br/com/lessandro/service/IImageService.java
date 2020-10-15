package br.com.lessandro.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.tika.exception.TikaException;

import br.com.lessandro.dto.ImageDto;
import br.com.lessandro.model.Image;
import br.com.lessandro.resources.exception.ValidationException;

public interface IImageService {

	void generateImageDisk(Long idImage, String filename, byte[] content)
			throws IOException, URISyntaxException, TikaException;

	byte[] convertBase64ToByteArray(String fileContent) throws UnsupportedEncodingException;

	ImageDto loadImage(String idImage) throws IOException, ValidationException;

	void generateImagesDisk(List<Image> images) throws ValidationException;
}