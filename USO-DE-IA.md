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



