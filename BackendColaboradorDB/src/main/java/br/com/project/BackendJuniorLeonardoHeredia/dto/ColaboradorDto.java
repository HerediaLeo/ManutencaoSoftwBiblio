package br.com.project.BackendJuniorLeonardoHeredia.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author leomh
 *
 * 10 de jul de 2020
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColaboradorDto {

	private Long id;
	
	private String nome;
	
	private String email;
	
	private String contato;
	
	private String cpf;
	
	private Date datanascimento;
	
	private Long cod_setor;
	
	private String desc_setor;
	
}
