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
        
        reset(mockService);
    }

    @Test
    void testGet() {
       
        List<Pessoa> pessoasMock = new ArrayList<>();
        when(mockService.consultarPessoas("Barbara", null)).thenReturn(pessoasMock);

        
        ResponseEntity<List<Pessoa>> response = apiController.get("Barbara", null);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pessoasMock, response.getBody());

        
        verify(mockService, times(1)).consultarPessoas("Barbara", null);
    }

    @Test
    void testPost() {
        
        Pessoa novaPessoa = new Pessoa("Nova Pessoa", 25, "Piada Nova", new String[]{"uva", "banana"});

        
        ResponseEntity<String> response = apiController.post(novaPessoa);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pessoa adicionada!", response.getBody());

        
        verify(mockService, times(1)).adicionarPessoa(novaPessoa);
    }

    @Test
    void testPostWithError() {
        
        Pessoa pessoaComErro = new Pessoa(null, 25, "Piada Nova", new String[]{"uva", "banana"});
        when(mockService.adicionarPessoa(pessoaComErro)).thenThrow(new RuntimeException("Campo nome é obrigatório"));

       
        ResponseEntity<String> response = apiController.post(pessoaComErro);

     
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Campo nome é obrigatório", response.getBody());

       
        verify(mockService, times(1)).adicionarPessoa(pessoaComErro);
    }

    @Test
    void testPut() {
        
        Pessoa pessoa = new Pessoa("Nova Pessoa", 25, null, new String[]{"uva", "banana"});
        List<Pessoa> pessoasAtualizadas = new ArrayList<>();
        when(mockService.atualizarPiadas(pessoa)).thenReturn(pessoasAtualizadas);

        
        ResponseEntity<List<Pessoa>> response = apiController.put(pessoa);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pessoasAtualizadas, response.getBody());

        
        verify(mockService, times(1)).atualizarPiadas(pessoa);
    }

    @Test
    void testDelete() {
        
        ResponseEntity<String> response = apiController.delete("Barbara");

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Barbara", response.getBody());
    }

   
}
