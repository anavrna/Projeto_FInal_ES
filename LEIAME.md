# Projeto de Teste de Software — Biblioteca

Trabalho de Engenharia de Software. Testamos um sistema de biblioteca em Java que contém defeitos. Não construímos o sistema: o trabalho é escrever a suíte de testes.

## Fases

| Fase | Técnica | Prazo | Situação |
|---|---|---|---|
| 1 | Caixa-preta: partição de equivalência e valor-limite | 24/11/2026 | Entregue |
| 2 | Caixa-branca: cobertura e complexidade ciclomática | 30/11/2026 | A fazer |
| 3 | TDD: classe `FilaReservas` | 30/11/2026 | A fazer |

## Estrutura do repositório

```
lib/biblioteca-1.0.jar          Sistema sob teste (compilado, sem código-fonte)
src/test/java/br/edu/biblioteca Testes JUnit 5, uma classe por método
PLANO-DE-TESTES.md              Classes de equivalência, valores-limite e teste de cada uma
DEFEITOS.md                     Falhas encontradas e defeitos prováveis
USO-DE-IA.md                    Como usamos IA no trabalho
pom.xml                         Configuração do Maven
```

## Testes da fase 1

| Arquivo | Método testado | Casos |
|---|---|---|
| `PrazoDiasTest` | `prazoDias` | 4 |
| `LimiteEmprestimosTest` | `limiteEmprestimos` | 4 |
| `DataDevolucaoTest` | `dataDevolucao` | 14 |
| `MultaCentavosTest` | `multaCentavos` | 24 |
| `PodeEmprestarTest` | `podeEmprestar` | 17 |
| `PodeRenovarTest` | `podeRenovar` | 15 |
| `MultaTotalCentavosTest` | `multaTotalCentavos` | 13 |
| `ExemploTest` | exemplo do kit | 1 |

Cada caso tem um código (ex.: `MC05`) que aparece no nome do teste ou no comentário da linha do `@CsvSource`. O mesmo código está no `PLANO-DE-TESTES.md` e no `DEFEITOS.md`.

## Como rodar

Requer **JDK 17 ou superior**.

- **Pela IDE:** abram a pasta como projeto Maven (IntelliJ, VS Code ou Eclipse) e rodem os testes.
- **Pelo terminal:**
  ```
  mvn test
  ```
  Os resultados ficam em `target/surefire-reports`.

## Resultado esperado

**92 testes: 52 passam e 40 falham.**

As falhas são esperadas: o resultado esperado de cada teste vem da especificação (Anexo A do enunciado), e uma falha indica um defeito do sistema. O `pom.xml` está configurado para não interromper o build quando um teste falha. Nunca ajustamos o valor esperado para o que o sistema devolve.

## Regras seguidas

- Os resultados esperados vêm sempre da especificação.
- Os nomes de classes e métodos são exatamente os do Anexo A.
- O `.jar` não foi descompilado.

## Equipe

- Ana Luiza
- Ana Vírna
- Maria Fernanda
- Pedro Paulo