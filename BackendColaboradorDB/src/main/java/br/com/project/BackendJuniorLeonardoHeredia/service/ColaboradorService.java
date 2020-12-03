package br.com.project.BackendJuniorLeonardoHeredia.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.project.BackendJuniorLeonardoHeredia.dto.ColaboradorDto;
import br.com.project.BackendJuniorLeonardoHeredia.dto.ColaboradorListaNegraDto;
import br.com.project.BackendJuniorLeonardoHeredia.model.Colaborador;
import br.com.project.BackendJuniorLeonardoHeredia.model.Setor;
import br.com.project.BackendJuniorLeonardoHeredia.repository.ColaboradorRepo;

/**
 * @author leomh
 *
 * 11 de jul de 2020
 */
@Service
public class ColaboradorService {
	
	public static final String URI = "https://5e74cb4b9dff120016353b04.mockapi.io/api/v1/blacklist";
			
	RestTemplate restTemplate = new RestTemplateBuilder().rootUri(URI).build();
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");
	
	@Autowired
	ColaboradorRepo colabrepo;

	public ColaboradorDto findById (Long id) {
		
		try {			
			ColaboradorDto dto = new ColaboradorDto();
			Colaborador model = new Colaborador();
			model = colabrepo.findById(id).get();
			
			dto.setNome(model.getNome());
			dto.setContato(model.getTelefone());
			dto.setCpf(model.getCpf());
			dto.setEmail(model.getEmail());
			dto.setDatanascimento(model.getData_nascimento());
			dto.setDesc_setor(model.getSetor().getDescricao());
						
			return dto;
		} catch (Exception e) {
			throw e;
		}				
	}
	

	public List<ColaboradorDto> getColabBySetor(Long setor_id){
		
		List<ColaboradorDto> dto = colabrepo.getColaboradorPorSetor(setor_id);
		
		return dto;
	}
	
	
	public boolean verifQuantMenorIdade(Long setor_id) {
		
		LocalDate dataHoje = LocalDate.now();		
		LocalDate dtNascFormatada;
		
		int qtdeMenor = 0;
		int qtdeTotalSetor = 0;
		boolean verificador = false;
				
		List<ColaboradorDto> colaboradoresDto = getColabBySetor(setor_id);
		Colaborador model = new Colaborador();
		
		qtdeTotalSetor = colaboradoresDto.size();
		
		for(ColaboradorDto c : colaboradoresDto) {			
			model.setNome(c.getNome());
			model.setEmail(c.getEmail());
			model.getSetor().setId(c.getCod_setor());
			model.setTelefone(c.getContato());
			model.setData_nascimento(c.getDatanascimento());
			model.setCpf(c.getCpf());
			
			dtNascFormatada = model.getData_nascimento().toInstant().atZone( ZoneId.systemDefault() ).toLocalDate();
			Period periodo = Period.between(dtNascFormatada, dataHoje);
						
			if(periodo.getYears() < 18) {
				qtdeMenor++;
				if(qtdeMenor > (qtdeTotalSetor / 5)){
					verificador = true;
				}
			}
			
		}
			
		return verificador;
	}
	
	
	
	public List<ColaboradorDto> findAll(){
		
		try {
			
			List<ColaboradorDto> dto = new ArrayList<>();
			List<Colaborador> model = new ArrayList<>();
			
			model = colabrepo.findAll();
			
			for(Colaborador c : model) {
				
				ColaboradorDto cdto = new ColaboradorDto();
				cdto.setId(c.getId());
				cdto.setNome(c.getNome());
				cdto.setDatanascimento(c.getData_nascimento());
				cdto.setCpf(c.getCpf());
				cdto.setEmail(c.getEmail());
				cdto.setContato(c.getTelefone());
				cdto.setCod_setor(c.getSetor().getId());
				cdto.setDesc_setor(c.getSetor().getDescricao());
				
				dto.add(cdto);
			}			
			return dto;
			
		} catch (Exception e) {
			throw e;
		}		
	}
	
	public void deleteColaborador(Long id) {
		try {
			
			colabrepo.deleteById(id);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public void deletarColaboradores() {		
		try {			
			colabrepo.deleteAll();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public ResponseEntity<List<ColaboradorListaNegraDto>> getListaNegra(){		
		
		return restTemplate.exchange("/",HttpMethod.GET,
							  		 null, new ParameterizedTypeReference<List<ColaboradorListaNegraDto>>() {}
		);
						
	}
	
	public List<ColaboradorListaNegraDto> getColaboradoresListaNegra(){
		
		return getListaNegra().getBody();
		
	}
	
	
	public boolean verificaListaNegra(ColaboradorDto colabAdd) {
		
		boolean verifica = false;
		
		List<ColaboradorListaNegraDto> colabProibidos;		
		colabProibidos = getListaNegra().getBody();
		
		for(ColaboradorListaNegraDto c : colabProibidos) {
			
			if(c.getCpf().equals(colabAdd.getCpf()))
				verifica = true;			
			else
				verifica = false;
		}		
		return verifica;
	}
	
	public Colaborador saveColab(ColaboradorDto dto) {
		Colaborador colabmodel = new Colaborador();
		Setor setormodel = new Setor();
		try {
			
			if(verificaListaNegra(dto) == false){
				
				setormodel.setId(dto.getCod_setor());
				setormodel.setDescricao(dto.getDesc_setor());
				
				colabmodel.setCpf(dto.getCpf());
				colabmodel.setData_nascimento(dto.getDatanascimento());
				colabmodel.setNome(dto.getNome());
				colabmodel.setTelefone(dto.getContato());
				colabmodel.setEmail(dto.getEmail());		
				colabmodel.setSetor(setormodel);
				
				
					colabrepo.save(colabmodel);					 
					
			}	
			return colabmodel;
		} 
		catch (Exception e) {
			throw e;
		}
			
	}
	
	

// fim
}
