package at.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import at.api.model.Pessoa;
import at.api.model.PiadaResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MockService {
	
	private List<Pessoa> pessoas;
	
	public MockService() {
		pessoas = new ArrayList<>();
		String frutas[] = { "maca", "pera" };
		pessoas.add(new Pessoa("Barbara", 32, null, frutas));
		pessoas.add(new Pessoa("Scarllet", 3, null, frutas));
	}

	public List<Pessoa> consultarPessoas(String nome, Integer idade) {
		if (nome != null) {
			return pessoas.stream().filter(pessoa -> pessoa.getNome().equals(nome)).toList();
		}
		if (idade != null) {
			return pessoas.stream().filter(pessoa -> pessoa.getIdade() == idade).toList();
		}
		return pessoas;
	}

	public Pessoa adicionarPessoa(Pessoa pessoa) {
		if (pessoa.getNome() == null) {
			throw new RuntimeException("Campo nome é obrigatorio");
		}
		if (pessoa.getIdade() == null) {
			throw new RuntimeException("Campo idade é obrigatorio");
		}
		pessoas.add(pessoa);
		return pessoa;
	}
	
	public List<Pessoa> atualizarPiadas(Pessoa pessoa) {
		List<Pessoa> pessoas = consultarPessoas(pessoa.getNome(), pessoa.getIdade());
		pessoas.forEach(p -> p.setPiada(consultarPiadaDaSorte()));
		return pessoas;
	}
	
	public String consultarPiadaDaSorte() {
        try {
        	RestTemplate restTemplate = new RestTemplate();
        	
            ResponseEntity<String> response = restTemplate.getForEntity("https://api.adviceslip.com/advice/55", String.class);
            log.info("Status da chamada à API externa: {}", response.getStatusCode());
            
            String body = response.getBody();
            PiadaResponse piadaResponse = new Gson().fromJson(body, PiadaResponse.class);
            
            return piadaResponse.getSlip().getAdvice();
        } catch (Exception e) {
            log.error("Erro na chamada à API externa: {}", e.getMessage());
            throw new RuntimeException("Erro na chamada à API externa");
        }
	}

}