package br.com.lessandro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lessandro.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	
}
