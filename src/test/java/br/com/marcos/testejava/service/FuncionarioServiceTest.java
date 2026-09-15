package br.com.marcos.testejava.service;

import br.com.marcos.testejava.model.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FuncionarioServiceTest {

    private List<Funcionario> funcionarios;

    @BeforeEach
    void montarListaComOsDezFuncionariosDoEnunciado() {
        funcionarios = FuncionarioService.criarFuncionarios();
    }

    @Test
    void deveCriarOsDezFuncionariosNaOrdemDoEnunciado() {
        assertEquals(10, funcionarios.size());
        assertEquals("Maria", funcionarios.get(0).getNome());
        assertEquals("João", funcionarios.get(1).getNome());
        assertEquals("Helena", funcionarios.get(9).getNome());
    }

    @Test
    void deveRemoverJoaoRestandoNoveFuncionarios() {
        FuncionarioService.removerPorNome(funcionarios, "João");

        assertEquals(9, funcionarios.size());
        assertFalse(funcionarios.stream().anyMatch(funcionario -> funcionario.getNome().equals("João")));
    }

    @Test
    void deveAplicarAumentoDeDezPorCentoNoSalarioDeTodosOsFuncionarios() {
        FuncionarioService.removerPorNome(funcionarios, "João");
        FuncionarioService.aplicarAumento(funcionarios, new BigDecimal("0.10"));

        assertEquals(new BigDecimal("2210.38"), salarioDe("Maria"));
        assertEquals(new BigDecimal("10819.75"), salarioDe("Caio"));
        assertEquals(new BigDecimal("2111.87"), salarioDe("Miguel"));
        assertEquals(new BigDecimal("2458.15"), salarioDe("Alice"));
        assertEquals(new BigDecimal("1740.99"), salarioDe("Heitor"));
        assertEquals(new BigDecimal("4479.02"), salarioDe("Arthur"));
        assertEquals(new BigDecimal("3319.20"), salarioDe("Laura"));
        assertEquals(new BigDecimal("1767.54"), salarioDe("Heloísa"));
        assertEquals(new BigDecimal("3079.92"), salarioDe("Helena"));
    }

    @Test
    void deveEncontrarCaioComoOFuncionarioMaisVelho() {
        FuncionarioService.removerPorNome(funcionarios, "João");

        Funcionario maisVelho = FuncionarioService.funcionarioMaisVelho(funcionarios);

        assertEquals("Caio", maisVelho.getNome());
    }

    @Test
    void deveAgruparOsNoveFuncionariosRestantesPorFuncao() {
        FuncionarioService.removerPorNome(funcionarios, "João");

        Map<String, List<Funcionario>> agrupados = FuncionarioService.agruparPorFuncao(funcionarios);

        assertEquals(2, agrupados.get("Operador").size());
        assertEquals(2, agrupados.get("Gerente").size());
        assertEquals(1, agrupados.get("Coordenador").size());
        assertEquals(1, agrupados.get("Diretor").size());
        assertEquals(1, agrupados.get("Recepcionista").size());
        assertEquals(1, agrupados.get("Contador").size());
        assertEquals(1, agrupados.get("Eletricista").size());
    }

    @Test
    void deveFiltrarApenasMariaEMiguelComoAniversariantesDeOutubroEDezembro() {
        FuncionarioService.removerPorNome(funcionarios, "João");

        List<Funcionario> aniversariantes = FuncionarioService.aniversariantesDeOutubroEDezembro(funcionarios);

        assertEquals(2, aniversariantes.size());
        assertTrue(aniversariantes.stream().anyMatch(funcionario -> funcionario.getNome().equals("Maria")));
        assertTrue(aniversariantes.stream().anyMatch(funcionario -> funcionario.getNome().equals("Miguel")));
    }

    @Test
    void deveSomarOTotalDosSalariosDosNoveFuncionariosAposOAumento() {
        FuncionarioService.removerPorNome(funcionarios, "João");
        FuncionarioService.aplicarAumento(funcionarios, new BigDecimal("0.10"));

        BigDecimal total = FuncionarioService.totalSalarios(funcionarios);

        assertEquals(new BigDecimal("31986.82"), total);
    }

    @Test
    void deveCalcularQuantidadeDeSalariosMinimosDeMaria() {
        BigDecimal quantidade = FuncionarioService.quantidadeSalariosMinimos(salarioDe("Maria"), new BigDecimal("1212.00"));

        assertEquals(new BigDecimal("1.66"), quantidade);
    }

    @Test
    void deveOrdenarOsNoveFuncionariosRestantesPorOrdemAlfabetica() {
        FuncionarioService.removerPorNome(funcionarios, "João");

        List<Funcionario> ordenados = FuncionarioService.ordenarPorNome(funcionarios);

        List<String> nomes = ordenados.stream().map(Funcionario::getNome).toList();
        assertEquals(List.of("Alice", "Arthur", "Caio", "Heitor", "Helena", "Heloísa", "Laura", "Maria", "Miguel"), nomes);
    }

    private BigDecimal salarioDe(String nome) {
        return funcionarios.stream()
                .filter(funcionario -> funcionario.getNome().equals(nome))
                .findFirst()
                .orElseThrow()
                .getSalario();
    }
}
