package br.com.lessandro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lessandro.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
//	@Query(value = "SELECT c FROM Cliente c where lower(c.nome) = :nome or c.cpf = :cpf") 
    Post buscaPorNomeCpf(@Param("nome") String nome, @Param("cpf") String cpf);

}
