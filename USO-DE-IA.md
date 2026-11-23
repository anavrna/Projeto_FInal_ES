# Uso de IA — Fase 1

**Ferramenta:** Claude (Anthropic), em 23/11/2026.

## O que pedimos à IA

1. Conferir se o kit da fase 1 tinha tudo o que o enunciado pede. A resposta foi que só havia o kit original: faltavam a suíte, o `PLANO-DE-TESTES.md`, o `DEFEITOS.md` e este arquivo.
2. Escrever os testes JUnit 5 dos 7 métodos do Anexo A, por partição de equivalência e valor-limite, num nível simples o bastante para a equipe explicar na arguição.
3. Rodar a suíte contra `lib/biblioteca-1.0.jar` e montar o `DEFEITOS.md` com as falhas.
4. Redigir o `PLANO-DE-TESTES.md` ligando cada classe e cada limite ao teste correspondente.

## O que aproveitamos

- Os 7 arquivos de teste (`*Test.java`), um por método, com o código de cada caso (PD01, MC05...) no nome ou no comentário.
- A estrutura do plano de testes e da tabela de defeitos.
- A ideia de testar cada condição de `podeEmprestar` isolada, com as outras em valores seguros.
- O caso DD08 (retirada num domingo), que a IA acrescentou depois de perceber que os testes de aluno não separavam "domingo da retirada" de "domingo do resultado".

## O que conferimos ou corrigimos

> Marquem `[x]` só no que a equipe realmente fizer, e acrescentem o que mais corrigirem.

- [ ] **Valores esperados:** refazer à mão as contas de multa (ex.: 30 dias = 10×100 + 20×200 = 5000) e conferir num calendário os dias da semana das datas do `DataDevolucaoTest`.
- [ ] **Interpretação de "renovam até 2 vezes":** discutir e confirmar a leitura "com 2 renovações feitas não pode mais renovar".
- [ ] **Execução:** o ambiente da IA não tinha acesso ao repositório Maven, então ela rodou os testes com um executor próprio. Rodar `mvn test` na nossa máquina e conferir que o resultado bate (92 testes: 52 passam e 40 falham).
- **Caixa-preta:** a IA não descompilou o `.jar`. Ela só listou as assinaturas públicas para confirmar que os nomes batem com o Anexo A. Os esperados vieram todos da especificação.
- _(Outros ajustes feitos pela equipe:)_
