package br.com.lessandro.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.lessandro.dto.AlbumDto;
import br.com.lessandro.model.Album;

@Mapper
public interface AlbumMapper extends GenericMapper<Album, AlbumDto> {

	AlbumMapper INSTANCE = Mappers.getMapper(AlbumMapper.class);

}
