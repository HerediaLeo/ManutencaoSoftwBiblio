package br.com.project.BackendJuniorLeonardoHeredia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.project.BackendJuniorLeonardoHeredia.dto.ColaboradorDto;
import br.com.project.BackendJuniorLeonardoHeredia.model.Colaborador;

/**
 * @author leomh
 *
 * 10 de jul de 2020
 */

public interface ColaboradorRepo extends JpaRepository<Colaborador, Long> {

	@Query(value = "select new br.com.project.BackendJuniorLeonardoHeredia.dto.ColaboradorDto"
			+ "(c.id,c.nome, c.email, c.telefone, c.cpf,c.data_nascimento,c.setor.id, c.setor.descricao)"
			+ "from Colaborador c where c.setor.id = :setor_id") 		
	List<ColaboradorDto> getColaboradorPorSetor(Long setor_id);
	
}
