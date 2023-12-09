package at.api.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import at.api.model.Pessoa;

@ExtendWith(MockitoExtension.class)
class MockServiceTest {

    @InjectMocks
    private MockService mockService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        mockService = new MockService();
    }

    @Test
    void testConsultarPessoasPorNome() {
        List<Pessoa> pessoas = mockService.consultarPessoas("Barbara", null);
        assertEquals(1, pessoas.size());
        assertEquals("Barbara", pessoas.get(0).getNome());
    }

    @Test
    void testConsultarPessoasPorIdade() {
        List<Pessoa> pessoas = mockService.consultarPessoas(null, 3);
        assertEquals(1, pessoas.size());
        assertEquals(3, pessoas.get(0).getIdade());
    }

    @Test
    void testAdicionarPessoa() {
        Pessoa novaPessoa = new Pessoa("Nova Pessoa", 25, null, new String[]{"uva", "banana"});
        assertDoesNotThrow(() -> mockService.adicionarPessoa(novaPessoa));
        List<Pessoa> pessoas = mockService.consultarPessoas(null, null);
        assertTrue(pessoas.contains(novaPessoa));
    }

    @Test
    void testAdicionarPessoaSemNome() {
        Pessoa pessoaComErro = new Pessoa(null, 25, null, new String[]{"uva", "banana"});
        RuntimeException exception = assertThrows(RuntimeException.class, () -> mockService.adicionarPessoa(pessoaComErro));
        assertEquals("Campo nome é obrigatorio", exception.getMessage());
    }

    @Test
    void testAdicionarPessoaSemIdade() {
        Pessoa pessoaComErro = new Pessoa("Nova Pessoa", null, null, new String[]{"uva", "banana"});
        RuntimeException exception = assertThrows(RuntimeException.class, () -> mockService.adicionarPessoa(pessoaComErro));
        assertEquals("Campo idade é obrigatorio", exception.getMessage());
    }

    @Test
    void testAtualizarPiadas() {
        Pessoa pessoa = new Pessoa("Barbara", 32, null, new String[]{"uva", "banana"});
        List<Pessoa> pessoasAtualizadas = mockService.atualizarPiadas(pessoa);
        assertEquals(1, pessoasAtualizadas.size());
        assertTrue(pessoasAtualizadas.stream().allMatch(p -> p.getPiada() != null));
    }

    @Test
    void testConsultarPiadaDaSorteComSucesso() {
        String piada = mockService.consultarPiadaDaSorte();
        assertEquals("Do not seek praise, seek criticism.", piada);
    }

}
