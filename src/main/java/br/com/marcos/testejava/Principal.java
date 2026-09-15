package br.com.marcos.testejava;

import br.com.marcos.testejava.model.Funcionario;
import br.com.marcos.testejava.service.FuncionarioService;
import br.com.marcos.testejava.util.Formatador;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Principal {

    private static final BigDecimal PERCENTUAL_AUMENTO = new BigDecimal("0.10");
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        List<Funcionario> funcionarios = FuncionarioService.criarFuncionarios();

        FuncionarioService.removerPorNome(funcionarios, "João");

        System.out.println("3.3 - Funcionarios cadastrados:");
        imprimirFuncionarios(funcionarios);

        FuncionarioService.aplicarAumento(funcionarios, PERCENTUAL_AUMENTO);

        System.out.println();
        System.out.println("3.6 - Funcionarios agrupados por funcao:");
        Map<String, List<Funcionario>> funcionariosPorFuncao = FuncionarioService.agruparPorFuncao(funcionarios);
        imprimirAgrupadosPorFuncao(funcionariosPorFuncao);

        System.out.println();
        System.out.println("3.8 - Aniversariantes de outubro e dezembro:");
        imprimirFuncionarios(FuncionarioService.aniversariantesDeOutubroEDezembro(funcionarios));

        System.out.println();
        System.out.println("3.9 - Funcionario mais velho:");
        Funcionario maisVelho = FuncionarioService.funcionarioMaisVelho(funcionarios);
        System.out.println(maisVelho.getNome() + " - " + FuncionarioService.calcularIdade(maisVelho.getDataNascimento()) + " anos");

        System.out.println();
        System.out.println("3.10 - Funcionarios em ordem alfabetica:");
        imprimirFuncionarios(FuncionarioService.ordenarPorNome(funcionarios));

        System.out.println();
        System.out.println("3.11 - Total dos salarios:");
        System.out.println(Formatador.formatarMoeda(FuncionarioService.totalSalarios(funcionarios)));

        System.out.println();
        System.out.println("3.12 - Salarios minimos por funcionario:");
        imprimirSalariosMinimos(funcionarios, SALARIO_MINIMO);
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
            imprimirFuncionarios(entrada.getValue());
        }
    }

    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios, BigDecimal salarioMinimo) {
        for (Funcionario funcionario : funcionarios) {
            BigDecimal quantidade = FuncionarioService.quantidadeSalariosMinimos(funcionario.getSalario(), salarioMinimo);
            System.out.println(funcionario.getNome() + ": " + Formatador.formatarNumero(quantidade) + " salarios minimos");
        }
    }
}
