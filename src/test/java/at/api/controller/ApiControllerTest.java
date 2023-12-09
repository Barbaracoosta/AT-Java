package at.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import at.api.model.Pessoa;
import at.api.service.MockService;

@ExtendWith(MockitoExtension.class)
class ApiControllerTest {

    @InjectMocks
    private ApiController apiController;

    @Mock
    private MockService mockService;

    @BeforeEach
    void setUp() {
        // Limpa o estado inicial do mockService antes de cada teste
        reset(mockService);
    }

    @Test
    void testGet() {
        // Configuração do Mock
        List<Pessoa> pessoasMock = new ArrayList<>();
        when(mockService.consultarPessoas("Barbara", null)).thenReturn(pessoasMock);

        // Executa o método a ser testado
        ResponseEntity<List<Pessoa>> response = apiController.get("Barbara", null);

        // Verifica o resultado da chamada do método
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pessoasMock, response.getBody());

        // Verifica se o método do mockService foi chamado corretamente
        verify(mockService, times(1)).consultarPessoas("Barbara", null);
    }

    @Test
    void testPost() {
        // Configuração do Mock
        Pessoa novaPessoa = new Pessoa("Nova Pessoa", 25, "Piada Nova", new String[]{"uva", "banana"});

        // Executa o método a ser testado
        ResponseEntity<String> response = apiController.post(novaPessoa);

        // Verifica o resultado da chamada do método
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pessoa adicionada!", response.getBody());

        // Verifica se o método do mockService foi chamado corretamente
        verify(mockService, times(1)).adicionarPessoa(novaPessoa);
    }

    @Test
    void testPostWithError() {
        // Configuração do Mock para simular uma exceção
        Pessoa pessoaComErro = new Pessoa(null, 25, "Piada Nova", new String[]{"uva", "banana"});
        when(mockService.adicionarPessoa(pessoaComErro)).thenThrow(new RuntimeException("Campo nome é obrigatório"));

        // Executa o método a ser testado
        ResponseEntity<String> response = apiController.post(pessoaComErro);

        // Verifica o resultado da chamada do método
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Campo nome é obrigatório", response.getBody());

        // Verifica se o método do mockService foi chamado corretamente
        verify(mockService, times(1)).adicionarPessoa(pessoaComErro);
    }

    @Test
    void testPut() {
        // Configuração do Mock
        Pessoa pessoa = new Pessoa("Nova Pessoa", 25, null, new String[]{"uva", "banana"});
        List<Pessoa> pessoasAtualizadas = new ArrayList<>();
        when(mockService.atualizarPiadas(pessoa)).thenReturn(pessoasAtualizadas);

        // Executa o método a ser testado
        ResponseEntity<List<Pessoa>> response = apiController.put(pessoa);

        // Verifica o resultado da chamada do método
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pessoasAtualizadas, response.getBody());

        // Verifica se o método do mockService foi chamado corretamente
        verify(mockService, times(1)).atualizarPiadas(pessoa);
    }

    @Test
    void testDelete() {
        // Executa o método a ser testado
        ResponseEntity<String> response = apiController.delete("Barbara");

        // Verifica o resultado da chamada do método
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Barbara", response.getBody());
    }

    // Adicione mais testes usando assertThrows conforme necessário para cobrir outros cenários.

    // ...
}
