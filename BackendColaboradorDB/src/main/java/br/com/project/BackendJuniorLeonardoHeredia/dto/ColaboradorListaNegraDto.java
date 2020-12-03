package br.com.project.BackendJuniorLeonardoHeredia.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author leomh
 *
 * 14 de jul de 2020
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColaboradorListaNegraDto {

	private Long id;
	private LocalDateTime createdAt;
	private String nome;		
	private String cpf;
	
}
