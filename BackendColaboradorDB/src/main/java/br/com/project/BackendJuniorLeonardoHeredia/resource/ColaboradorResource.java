package br.com.project.BackendJuniorLeonardoHeredia.resource;

import java.io.IOException;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.project.BackendJuniorLeonardoHeredia.dto.ColaboradorDto;
import br.com.project.BackendJuniorLeonardoHeredia.dto.ColaboradorListaNegraDto;
import br.com.project.BackendJuniorLeonardoHeredia.model.Colaborador;
import br.com.project.BackendJuniorLeonardoHeredia.service.ColaboradorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * @author leomh
 *
 * 13 de jul de 2020
 */
@RestController
@Api(value = "/")
@RequestMapping("/api/public/colaborador")
public class ColaboradorResource {

	
	
	@Autowired
	ColaboradorService cserv;
	
	@GetMapping("/findColabById/{id}")
	@ApiOperation(value = "Retorna os dados de um colaborador passando um id como parâmetro")
	public ColaboradorDto findColabById(@PathVariable Long id) {
	
		return cserv.findById(id);
			
	}
	
	@GetMapping("/findAllColab")
	@ApiOperation(value = "Retorna uma lista com todos os colaboradores")
	public List<ColaboradorDto> findAllColab(){
		
		return cserv.findAll();
		
	}
	
	@GetMapping("/findColabBySetor/{setor_id}")
	@ApiOperation(value = "Retorna a lista de colaboradores agrupados por setor.")
	public List<ColaboradorDto> findColabBySetor(@PathVariable Long setor_id){
		
		return cserv.getColabBySetor(setor_id);
		
	}
	
	@GetMapping("/removerColaborador/{id}")
	@ApiOperation(value = "Remove um colaborador passando o id do colaborador.")
	public void removeColaborador(@PathVariable Long id) {
		
		cserv.deleteColaborador(id);
		
	}
	
	@GetMapping("/removerTodos")
	@ApiOperation(value = "Remove todos os colaboradores de uma vez.")
	public void removerTodos() {
		
		cserv.deletarColaboradores();
		
	}
	
	@PostMapping("/save")	
	public ResponseEntity<?> saveColaborador(@RequestBody ColaboradorDto dto) throws IOException{
		Colaborador colabAdd;
		
		colabAdd = cserv.saveColab(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(colabAdd);
		
	}
	
	
}
