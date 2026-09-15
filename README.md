# Teste Prático - Iniflex

Solução para o teste técnico de Desenvolvedor Java Júnior. Cadastra uma lista
de funcionários e aplica sobre ela as operações de remoção, reajuste
salarial, agrupamento, filtragem, ordenação e cálculos financeiros pedidas
no enunciado.

## Tecnologias utilizadas

- Java 21
- Maven
- JUnit 5

## Como executar

```
mvn compile exec:java
```

Ou, sem o plugin de execução:

```
mvn compile
java -cp target/classes br.com.marcos.testejava.Principal
```

## Como executar os testes

```
mvn test
```

## Estrutura do projeto

```
src/main/java/br/com/marcos/testejava/
    model/
        Pessoa.java
        Funcionario.java
    service/
        FuncionarioService.java
    util/
        Formatador.java
    Principal.java
src/test/java/br/com/marcos/testejava/
    service/
        FuncionarioServiceTest.java
```

A `FuncionarioService` concentra as regras de negócio (criação da lista,
remoção, reajuste, agrupamento, filtragem, ordenação e cálculos) em métodos
públicos. A `Principal` fica responsável apenas por orquestrar as chamadas e
imprimir os resultados no formato pedido pelo enunciado. Os testes exercitam
a `FuncionarioService` diretamente, com a mesma lista de 10 funcionários (9
após a remoção) usada pelo programa.

## Requisitos implementados

| Requisito | Descrição                                               | Status |
|-----------|----------------------------------------------------------|--------|
| 3.1       | Inserção dos funcionários                                 | OK     |
| 3.2       | Remoção do funcionário João                                | OK     |
| 3.3       | Impressão de todos os funcionários                         | OK     |
| 3.4       | Aumento de 10% no salário com BigDecimal                   | OK     |
| 3.5       | Agrupamento por função (Map)                                | OK     |
| 3.6       | Impressão dos funcionários agrupados por função            | OK     |
| 3.8       | Aniversariantes de outubro e dezembro                      | OK     |
| 3.9       | Funcionário com maior idade                                 | OK     |
| 3.10      | Ordenação alfabética por nome                               | OK     |
| 3.11      | Soma total dos salários                                     | OK     |
| 3.12      | Quantidade de salários mínimos por funcionário              | OK     |
