package br.com.project.BackendJuniorLeonardoHeredia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author leomh
 *
 * 10 de jul de 2020
 */

@Data
@Entity
@Table(name = "setores")
public class Setor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id" , unique = true)
	private Long id;
	
	@Column(nullable = false)
	private String descricao;
	
}
