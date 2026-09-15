package br.com.marcos.testejava;

import br.com.marcos.testejava.model.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrincipalTest {

    private List<Funcionario> funcionarios;

    @BeforeEach
    void montarLista() {
        funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("1919.88"), "Diretor"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
    }

    @SuppressWarnings("unchecked")
    private <T> T chamarMetodoPrivado(String nome, Class<?>[] tiposParametros, Object... parametros) throws Exception {
        Method metodo = Principal.class.getDeclaredMethod(nome, tiposParametros);
        metodo.setAccessible(true);
        try {
            return (T) metodo.invoke(null, parametros);
        } catch (InvocationTargetException e) {
            throw (Exception) e.getCause();
        }
    }

    @Test
    void deveAplicarAumentoDeDezPorCentoNoSalario() throws Exception {
        chamarMetodoPrivado("aplicarAumento", new Class[]{List.class, BigDecimal.class}, funcionarios, new BigDecimal("0.10"));

        assertEquals(new BigDecimal("2210.38"), funcionarios.get(0).getSalario());
        assertEquals(new BigDecimal("10819.75"), funcionarios.get(1).getSalario());
    }

    @Test
    void deveCalcularIdadeAPartirDaDataDeNascimento() throws Exception {
        LocalDate nascimento = LocalDate.now().minusYears(30).minusDays(1);
        int idade = chamarMetodoPrivado("calcularIdade", new Class[]{LocalDate.class}, nascimento);

        assertEquals(30, idade);
    }

    @Test
    void deveEncontrarOFuncionarioMaisVelho() throws Exception {
        Funcionario maisVelho = chamarMetodoPrivado("funcionarioMaisVelho", new Class[]{List.class}, funcionarios);

        assertEquals("Caio", maisVelho.getNome());
    }

    @Test
    void deveCalcularQuantidadeDeSalariosMinimos() throws Exception {
        BigDecimal quantidade = chamarMetodoPrivado("quantidadeSalariosMinimos",
                new Class[]{BigDecimal.class, BigDecimal.class}, new BigDecimal("2009.44"), new BigDecimal("1212.00"));

        assertEquals(new BigDecimal("1.66"), quantidade);
    }

    @Test
    void deveAgruparFuncionariosPorFuncao() throws Exception {
        Map<String, List<Funcionario>> agrupados = chamarMetodoPrivado("agruparPorFuncao", new Class[]{List.class}, funcionarios);

        assertEquals(2, agrupados.get("Operador").size());
        assertEquals(2, agrupados.get("Gerente").size());
        assertEquals(1, agrupados.get("Coordenador").size());
    }

    @Test
    void deveSomarOTotalDosSalarios() throws Exception {
        BigDecimal total = chamarMetodoPrivado("totalSalarios", new Class[]{List.class}, funcionarios);

        assertEquals(new BigDecimal("21165.56"), total);
    }
}
