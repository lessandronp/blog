package br.com.lessandro.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lessandro.model.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
	
	Page<Album> findByUser(Long userId, Pageable pageable);
	
}
