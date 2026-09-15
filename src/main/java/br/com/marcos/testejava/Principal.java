package br.com.marcos.testejava;

import br.com.marcos.testejava.model.Funcionario;
import br.com.marcos.testejava.util.Formatador;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {

    private static final BigDecimal PERCENTUAL_AUMENTO = new BigDecimal("0.10");
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        List<Funcionario> funcionarios = criarFuncionarios();

        removerFuncionario(funcionarios, "João");

        System.out.println("3.3 - Funcionarios cadastrados:");
        imprimirFuncionarios(funcionarios);

        aplicarAumento(funcionarios, PERCENTUAL_AUMENTO);

        System.out.println();
        System.out.println("3.6 - Funcionarios agrupados por funcao:");
        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparPorFuncao(funcionarios);
        imprimirAgrupadosPorFuncao(funcionariosPorFuncao);

        System.out.println();
        System.out.println("3.8 - Aniversariantes de outubro e dezembro:");
        imprimirFuncionarios(aniversariantesDeOutubroEDezembro(funcionarios));

        System.out.println();
        System.out.println("3.9 - Funcionario mais velho:");
        Funcionario maisVelho = funcionarioMaisVelho(funcionarios);
        System.out.println(maisVelho.getNome() + " - " + calcularIdade(maisVelho.getDataNascimento()) + " anos");

        System.out.println();
        System.out.println("3.10 - Funcionarios em ordem alfabetica:");
        imprimirFuncionarios(ordenarPorNome(funcionarios));

        System.out.println();
        System.out.println("3.11 - Total dos salarios:");
        System.out.println(Formatador.formatarMoeda(totalSalarios(funcionarios)));

        System.out.println();
        System.out.println("3.12 - Salarios minimos por funcionario:");
        imprimirSalariosMinimos(funcionarios, SALARIO_MINIMO);
    }

    private static List<Funcionario> criarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("1919.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
        return funcionarios;
    }

    private static void removerFuncionario(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals(nome));
    }

    private static void aplicarAumento(List<Funcionario> funcionarios, BigDecimal percentual) {
        for (Funcionario funcionario : funcionarios) {
            BigDecimal reajuste = funcionario.getSalario().multiply(percentual);
            BigDecimal novoSalario = funcionario.getSalario().add(reajuste).setScale(2, RoundingMode.HALF_UP);
            funcionario.setSalario(novoSalario);
        }
    }

    private static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    private static List<Funcionario> aniversariantesDeOutubroEDezembro(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .filter(funcionario -> {
                    int mes = funcionario.getDataNascimento().getMonthValue();
                    return mes == 10 || mes == 12;
                })
                .collect(Collectors.toList());
    }

    private static Funcionario funcionarioMaisVelho(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElseThrow();
    }

    private static int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    private static List<Funcionario> ordenarPorNome(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
    }

    private static BigDecimal totalSalarios(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal quantidadeSalariosMinimos(BigDecimal salario, BigDecimal salarioMinimo) {
        return salario.divide(salarioMinimo, 2, RoundingMode.HALF_UP);
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario.getNome()
                    + " - Nascimento: " + Formatador.formatarData(funcionario.getDataNascimento())
                    + " - Salario: " + Formatador.formatarMoeda(funcionario.getSalario())
                    + " - Funcao: " + funcionario.getFuncao());
        }
    }

    private static void imprimirAgrupadosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        for (Map.Entry<String, List<Funcionario>> entrada : funcionariosPorFuncao.entrySet()) {
            System.out.println(entrada.getKey() + ":");
            for (Funcionario funcionario : entrada.getValue()) {
                System.out.println("  " + funcionario.getNome());
            }
        }
    }

    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios, BigDecimal salarioMinimo) {
        for (Funcionario funcionario : funcionarios) {
            BigDecimal quantidade = quantidadeSalariosMinimos(funcionario.getSalario(), salarioMinimo);
            System.out.println(funcionario.getNome() + ": " + Formatador.formatarNumero(quantidade) + " salarios minimos");
        }
    }
}
