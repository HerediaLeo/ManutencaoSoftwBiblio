package br.com.project.BackendJuniorLeonardoHeredia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author leomh
 *
 * 10 de jul de 2020
 */

@Data
@Entity
@Table(name = "colaborador")
public class Colaborador {
	
		
	@Id // especificando que este atributo é uma chave primária.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;
	
	/* Um funcionário tem um somente um CPF. */
	@Column(name = "cpf" , unique = true)
	private String cpf;
	
	@Column(name = "dt_nascimento")
	private Date data_nascimento;
	
	private String nome;
	
	private String telefone;
	
	private String email;	
		
	/* Vários funcionários tem 1 setor */
	@ManyToOne
	@JoinColumn(name = "setor_id")
	private Setor setor;
	


	
}
