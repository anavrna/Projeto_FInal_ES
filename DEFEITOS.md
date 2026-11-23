# Defeitos encontrados — Fase 1 (caixa-preta)

Execução da suíte contra `lib/biblioteca-1.0.jar`: **92 casos, 52 passaram e 40 falharam.**

O esperado vem sempre do Anexo A. Cada linha abaixo é uma falha observada. A última coluna agrupa as falhas que parecem ter **a mesma causa** (D1, D2, ...). O agrupamento é só uma hipótese pelo comportamento observado, porque na fase 1 não vemos o código.

| # | Método | Entrada | Esperado | Obtido | Classe / limite que gerou o teste | Teste | Defeito |
|---|---|---|---|---|---|---|---|
| 1 | prazoDias | VISITANTE | 7 | 10 | classe "visitante" | PD03 | D1 |
| 2 | dataDevolucao | PROFESSOR, 06/11/2026 | 07/12/2026 | 06/12/2026 | resultado no domingo (limite sáb→dom) | DD07 | D2 |
| 3 | dataDevolucao | PROFESSOR, 22/11/2026 | 22/12/2026 | 23/12/2026 | retirada no domingo, resultado em dia útil | DD08 | D2 |
| 4 | dataDevolucao | VISITANTE, 23/11/2026 | 30/11/2026 | 03/12/2026 | resultado em dia útil, visitante | DD09 | D1 |
| 5 | dataDevolucao | VISITANTE, 21/11/2026 | 28/11/2026 | 01/12/2026 | resultado no sábado, visitante | DD10 | D1 |
| 6 | dataDevolucao | VISITANTE, 22/11/2026 | 30/11/2026 | 03/12/2026 | resultado no domingo, visitante | DD11 | D1 + D2 |
| 7 | dataDevolucao | VISITANTE, 27/12/2026 | 04/01/2027 | 07/01/2027 | domingo na virada do ano | DD12 | D1 + D2 |
| 8 | multaCentavos | ALUNO, 11 | 1200 | 1100 | limite: 1º dia a R$ 2,00 | MC05 | D3 |
| 9 | multaCentavos | ALUNO, 31 | 5000 | 5200 | limite: acima do teto | MC09 | D4 |
| 10 | multaCentavos | ALUNO, 100 | 5000 | 19000 | classe "acima do teto" | MC10 | D4 |
| 11 | multaCentavos | VISITANTE, 11 | 1200 | 1100 | limite: 1º dia a R$ 2,00 | MC12 | D3 |
| 12 | multaCentavos | VISITANTE, 31 | 5000 | 5200 | limite: acima do teto | MC13 | D4 |
| 13 | multaCentavos | PROFESSOR, 3 | 0 | 100 | limite: último dia de carência | MC15 | D5 |
| 14 | multaCentavos | PROFESSOR, 4 | 100 | 200 | limite: 1º dia cobrado | MC16 | D5 |
| 15 | multaCentavos | PROFESSOR, 13 | 1000 | 1100 | limite: 10º dia cobrado | MC17 | D5 |
| 16 | multaCentavos | PROFESSOR, 14 | 1200 | 1400 | limite: 11º dia cobrado | MC18 | D5 |
| 17 | multaCentavos | PROFESSOR, 33 | 5000 | 5200 | limite: atinge o teto | MC19 | D4 + D5 |
| 18 | multaCentavos | PROFESSOR, 34 | 5000 | 5400 | limite: acima do teto | MC20 | D4 + D5 |
| 19 | multaCentavos | ALUNO, −1 | exceção | nenhuma exceção | classe inválida: dias negativos | MC21 | D6 |
| 20 | multaCentavos | PROFESSOR, −1 | exceção | nenhuma exceção | classe inválida: dias negativos | MC22 | D6 |
| 21 | multaCentavos | VISITANTE, −1 | exceção | nenhuma exceção | classe inválida: dias negativos | MC23 | D6 |
| 22 | podeEmprestar | ALUNO, 3, 0, false | false | true | limite: ativos = limite (3) | PE03 | D7 |
| 23 | podeEmprestar | PROFESSOR, 8, 0, false | false | true | limite: ativos = limite (8) | PE05 | D7 |
| 24 | podeEmprestar | VISITANTE, 1, 0, false | false | true | limite: ativos = limite (1) | PE07 | D7 |
| 25 | podeEmprestar | ALUNO, 0, 1001, false | false | true | limite: multa 1 centavo acima de 1000 | PE09 | D7 |
| 26 | podeEmprestar | PROFESSOR, 0, 1001, false | false | true | classe "multa > 1000" | PE11 | D7 |
| 27 | podeEmprestar | ALUNO, 0, 0, true | false | true | classe "possui atraso" | PE12 | D7 |
| 28 | podeEmprestar | PROFESSOR, 0, 0, true | false | true | classe "possui atraso" | PE13 | D7 |
| 29 | podeEmprestar | VISITANTE, 0, 0, true | false | true | classe "possui atraso" | PE14 | D7 |
| 30 | podeEmprestar | ALUNO, 0, −1, false | exceção | nenhuma exceção | classe inválida: multa negativa | PE16 | D8 |
| 31 | podeRenovar | PROFESSOR, 3, false, false | false | true | limite: professor atingiu 3 renovações | PR06 | D9 |
| 32 | podeRenovar | VISITANTE, 0, false, false | false | true | classe "visitante nunca renova" | PR07 | D10 |
| 33 | multaTotalCentavos | ALUNO, {12} | 1400 | 0 | limite: lista com 1 elemento | MT02 | D11 |
| 34 | multaTotalCentavos | ALUNO, {5, 12, 0} | 1900 | 1400 | classe "vários elementos" | MT03 | D11 |
| 35 | multaTotalCentavos | ALUNO, {40, 1} | 5100 | 100 | classe "um empréstimo passa do teto" | MT04 | D11 |
| 36 | multaTotalCentavos | PROFESSOR, {3, 3} | 0 | 100 | carência do professor em cada empréstimo | MT06 | D11 + D5 |
| 37 | multaTotalCentavos | ALUNO, {30, 29} | 9800 | 4800 | limite: logo abaixo do teto total | MT07 | D11 |
| 38 | multaTotalCentavos | ALUNO, {30, 30} | 10000 | 5000 | limite: exatamente no teto total | MT08 | D11 |
| 39 | multaTotalCentavos | ALUNO, {30, 30, 1} | 10000 | 5100 | limite: logo acima do teto total | MT09 | D11 |
| 40 | multaTotalCentavos | ALUNO, {5, −1} | exceção | nenhuma exceção | classe inválida: elemento negativo | MT11 | D12 |

---

## Defeitos prováveis (agrupamento das falhas)

| Defeito | Descrição pelo comportamento observado | Falhas |
|---|---|---|
| D1 | `prazoDias(VISITANTE)` devolve 10 em vez de 7. Por isso toda `dataDevolucao` de visitante sai 3 dias depois. | 1, 4–7 |
| D2 | `dataDevolucao` não adia quando o **resultado** cai no domingo (DD07). Em vez disso, adia quando a **retirada** é num domingo (DD08). Parece olhar o dia errado. Nos testes de aluno isso não aparece porque 14 dias é múltiplo de 7: retirada e resultado caem no mesmo dia da semana. | 2, 3, 6, 7 |
| D3 | O 11º dia cobrado sai a R$ 1,00 em vez de R$ 2,00 (erro no limite 10/11). | 8, 11 |
| D4 | O teto de R$ 50,00 por empréstimo não é respeitado acima de 30 dias cobrados. | 9, 10, 12, 17, 18 |
| D5 | A carência do professor parece ser de 2 dias, não de 3 (3 dias já cobra 100; 4 dias cobra 200). | 13–18, 36 |
| D6 | Dias de atraso negativos não lançam exceção. | 19–21 |
| D7 | `podeEmprestar` devolve `true` mesmo quando uma das três condições é falsa. Parece exigir só **uma** das condições, e não as três juntas. | 22–29 |
| D8 | Multa pendente negativa não lança exceção. Já empréstimos ativos negativos lançam corretamente (PE15 passou). | 30 |
| D9 | Professor com 3 renovações feitas ainda pode renovar (permite a 4ª). O aluno está correto no limite de 2 (PR03 passou). | 31 |
| D10 | Visitante consegue renovar. | 32 |
| D11 | `multaTotalCentavos` parece **ignorar o primeiro empréstimo da lista**: `{12}` dá 0 e `{5, 12, 0}` dá 1400 (só o 12). | 33–39 |
| D12 | Elemento negativo na lista não lança exceção. | 40 |

### Observações

- Um defeito pode derrubar vários testes, e um teste pode falhar por dois defeitos ao mesmo tempo (ex.: falha 6 = D1 + D2). Por isso testamos cada regra **isolada**, com as outras entradas em valores seguros.
- Por causa do D11, **não conseguimos confirmar** se o teto total de R$ 100,00 funciona: as somas sempre saem menores do que deveriam. Os testes MT07–MT09 continuam corretos pela especificação e vão revelar o teto quando o D11 for corrigido.
- Testes que **passaram** e mostram partes corretas do sistema: tipo nulo em todos os métodos, prazos de aluno e professor, todos os limites de empréstimo, limite de 2 renovações do aluno, reserva e atraso em `podeRenovar`, lista nula e lista vazia em `multaTotalCentavos`.
