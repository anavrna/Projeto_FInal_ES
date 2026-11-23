# Plano de testes — Fase 1 (caixa-preta)

Todos os resultados esperados vêm da **especificação (Anexo A)**. Nenhum valor foi tirado do que o sistema devolve.

**Técnicas usadas**

- **Partição de equivalência (PE):** dividimos cada entrada em grupos (classes) em que o sistema deveria se comportar do mesmo jeito. Testamos **um representante** de cada classe válida e de cada classe inválida.
- **Análise de valor-limite (VL):** os defeitos costumam aparecer nas fronteiras entre classes. Para cada fronteira testamos o valor **exatamente no limite** e o **vizinho de cada lado** (limite − 1 e limite + 1).

**Como ler as tabelas**

- **V** = classe válida; **I** = classe inválida (deve lançar `IllegalArgumentException`).
- A coluna "Teste" traz o código do caso. Ele aparece no nome do método de teste (ex.: `PD01_alunoTem14Dias`) ou no comentário da linha do `@CsvSource` (ex.: `// MC05`).
- Arquivos de teste: `src/test/java/br/edu/biblioteca/<Método>Test.java`.

**Regra comum a todos os métodos:** `tipo == null` → `IllegalArgumentException`. Por isso cada método tem um teste de tipo nulo.

---

## 1. `prazoDias(TipoUsuario tipo)` — `PrazoDiasTest`

Especificação: aluno 14, professor 30, visitante 7.

| Classe | Tipo | Esperado | Teste |
|---|---|---|---|
| C1 tipo = ALUNO | V | 14 | PD01 |
| C2 tipo = PROFESSOR | V | 30 | PD02 |
| C3 tipo = VISITANTE | V | 7 | PD03 |
| C4 tipo = null | I | exceção | PD04 |

Valores-limite: a entrada é um enum, sem faixa numérica. Por isso cada valor do enum é uma classe própria e não há limites.

---

## 2. `limiteEmprestimos(TipoUsuario tipo)` — `LimiteEmprestimosTest`

Especificação: aluno 3, professor 8, visitante 1.

| Classe | Tipo | Esperado | Teste |
|---|---|---|---|
| C1 ALUNO | V | 3 | LE01 |
| C2 PROFESSOR | V | 8 | LE02 |
| C3 VISITANTE | V | 1 | LE03 |
| C4 null | I | exceção | LE04 |

Valores-limite: não há (enum). Os limites de 3, 8 e 1 são testados em `podeEmprestar`.

---

## 3. `dataDevolucao(LocalDate retirada, TipoUsuario tipo)` — `DataDevolucaoTest`

Especificação: data = retirada + prazo do tipo. Se cair num **domingo**, passa para a **segunda** seguinte. Sábado é dia normal.

**Classes de equivalência**

| Classe | Tipo | Testes |
|---|---|---|
| C1 resultado cai em dia útil (seg–sex) → mesma data | V | DD01, DD02, DD05, DD08, DD09 |
| C2 resultado cai no sábado → mesma data | V | DD03, DD06, DD10 |
| C3 resultado cai no domingo → segunda seguinte | V | DD04, DD07, DD11, DD12 |
| C4 cada tipo de usuário (ALUNO, PROFESSOR, VISITANTE) | V | aluno DD01–04; professor DD05–08; visitante DD09–12 |
| C5 retirada = null | I | DD13 |
| C6 tipo = null | I | DD14 |

**Valores-limite** (a fronteira é a passagem do sábado para o domingo):

| Limite | Por quê | Teste |
|---|---|---|
| resultado na sexta | último dia útil antes do fim de semana | DD02 |
| resultado no sábado | último dia que **não** é adiado | DD03, DD06, DD10 |
| resultado no domingo | primeiro dia que **é** adiado | DD04, DD07, DD11 |
| domingo na virada de ano (03/01/2027 → 04/01/2027) | a soma atravessa mês e ano | DD12 |

**Caso extra (DD08):** retirada num **domingo** com resultado numa **terça**. A regra fala do dia do **resultado**, então o dia da retirada não deveria importar e a data não pode ser adiada.

Datas de referência: 21/11/2026 = sábado, 22/11/2026 = domingo, 23/11/2026 = segunda.

---

## 4. `multaCentavos(TipoUsuario tipo, int diasAtraso)` — `MultaCentavosTest`

Especificação:
- professor tem carência: os 3 primeiros dias não são cobrados; os outros tipos pagam todos os dias;
- do 1º ao 10º dia cobrado: 100/dia; do 11º em diante: 200/dia;
- teto de 5000 por empréstimo;
- dias negativos → exceção.

Contas úteis: 10 dias cobrados = 1000; 30 dias cobrados = 10×100 + 20×200 = **5000** (já é o teto).

**Classes de equivalência de `diasAtraso`** (aluno e visitante; para professor somar 3 dias):

| Classe | Tipo | Faixa (aluno/visitante) | Faixa (professor) | Teste representante |
|---|---|---|---|---|
| C1 negativo | I | < 0 | < 0 | MC21, MC22, MC23 |
| C2 sem cobrança | V | 0 | 0 a 3 | MC01, MC14 |
| C3 faixa de R$ 1,00 | V | 1 a 10 | 4 a 13 | MC03 (5 dias) |
| C4 faixa de R$ 2,00, abaixo do teto | V | 11 a 29 | 14 a 32 | MC06 (12 dias, exemplo do Anexo A) |
| C5 teto | V | ≥ 30 | ≥ 33 | MC10 (100 dias) |
| C6 tipo null | I | — | — | MC24 |

**Valores-limite**

| Fronteira | Aluno | Esperado | Teste | Professor | Esperado | Teste |
|---|---|---|---|---|---|---|
| negativo / zero | −1 | exceção | MC21 | −1 | exceção | MC22 |
| | 0 | 0 | MC01 | 0 | 0 | MC14 |
| fim da carência | — | — | — | 3 | 0 | MC15 |
| 1º dia cobrado | 1 | 100 | MC02 | 4 | 100 | MC16 |
| último dia a R$ 1,00 | 10 | 1000 | MC04 | 13 | 1000 | MC17 |
| 1º dia a R$ 2,00 | 11 | 1200 | MC05 | 14 | 1200 | MC18 |
| logo abaixo do teto | 29 | 4800 | MC07 | — | — | — |
| exatamente no teto | 30 | 5000 | MC08 | 33 | 5000 | MC19 |
| acima do teto | 31 | 5000 | MC09 | 34 | 5000 | MC20 |

Visitante (mesma regra do aluno, conferida nos pontos principais): 1 → 100 (MC11), 11 → 1200 (MC12), 31 → 5000 (MC13), −1 → exceção (MC23).

---

## 5. `podeEmprestar(tipo, emprestimosAtivos, multaPendenteCentavos, possuiAtraso)` — `PodeEmprestarTest`

Especificação: `true` **somente se** as três condições valerem juntas:
- (a) `emprestimosAtivos < limite` do tipo (3 / 8 / 1);
- (b) `multaPendenteCentavos <= 1000`;
- (c) `possuiAtraso == false`.

Ativos ou multa negativos → exceção.

**Estratégia:** em cada teste **só uma** condição fica no limite ou falsa. As outras ficam num valor seguro (0 empréstimos, multa 0, sem atraso). Assim, quando um teste falha, sabemos qual condição está errada.

**Classes de equivalência**

| Entrada | Classe | Tipo | Teste |
|---|---|---|---|
| emprestimosAtivos | C1 negativo | I | PE15 |
| | C2 0 ≤ ativos < limite | V (contribui `true`) | PE01, PE02, PE04, PE06 |
| | C3 ativos ≥ limite | V (resultado `false`) | PE03, PE05, PE07 |
| multaPendente | C4 negativa | I | PE16 |
| | C5 0 a 1000 | V (contribui `true`) | PE08, PE10 |
| | C6 > 1000 | V (resultado `false`) | PE09, PE11 |
| possuiAtraso | C7 false | V (contribui `true`) | PE01 |
| | C8 true | V (resultado `false`) | PE12, PE13, PE14 |
| tipo | C9 null | I | PE17 |

**Valores-limite**

| Fronteira | Valores testados | Testes |
|---|---|---|
| ativos: aluno (limite 3) | 0, 2 → true; 3 → false | PE01, PE02, PE03 |
| ativos: professor (limite 8) | 7 → true; 8 → false | PE04, PE05 |
| ativos: visitante (limite 1) | 0 → true; 1 → false | PE06, PE07 |
| ativos: zero / negativo | 0 → válido; −1 → exceção | PE01, PE15 |
| multa: R$ 10,00 | 999 → true; 1000 → true; 1001 → false | PE10, PE08, PE09 |
| multa: zero / negativo | 0 → válido; −1 → exceção | PE01, PE16 |

---

## 6. `podeRenovar(tipo, renovacoesFeitas, possuiReserva, emAtraso)` — `PodeRenovarTest`

Especificação:
- visitante nunca renova;
- aluno renova até 2 vezes; professor, até 3;
- só renova sem reserva e sem atraso;
- renovações negativas → exceção, **para qualquer tipo**.

**Nossa interpretação de "até 2 vezes":** o total de renovações não passa de 2. Logo, com `renovacoesFeitas` = 0 ou 1 o aluno pode renovar (a 1ª ou a 2ª vez); com 2 já feitas, não pode. O professor segue a mesma lógica com 3.

**Classes de equivalência**

| Entrada | Classe | Tipo | Teste |
|---|---|---|---|
| tipo + renovações | C1 aluno, 0 ≤ feitas < 2 | V (true) | PR01, PR02 |
| | C2 aluno, feitas ≥ 2 | V (false) | PR03 |
| | C3 professor, 0 ≤ feitas < 3 | V (true) | PR04, PR05 |
| | C4 professor, feitas ≥ 3 | V (false) | PR06 |
| | C5 visitante (qualquer número ≥ 0) | V (false) | PR07 |
| | C6 feitas negativo (qualquer tipo) | I | PR12, PR13, PR14 |
| possuiReserva | C7 true | V (false) | PR08, PR09 |
| emAtraso | C8 true | V (false) | PR10, PR11 |
| tipo | C9 null | I | PR15 |

**Valores-limite**

| Fronteira | Valores testados | Testes |
|---|---|---|
| aluno: máximo 2 | 0 → true; 1 → true; 2 → false | PR01, PR02, PR03 |
| professor: máximo 3 | 0 → true; 2 → true; 3 → false | PR04, PR05, PR06 |
| zero / negativo | 0 → válido; −1 → exceção (aluno, professor **e visitante**) | PR01, PR12–PR14 |

---

## 7. `multaTotalCentavos(TipoUsuario tipo, int[] diasAtrasoPorEmprestimo)` — `MultaTotalCentavosTest`

Especificação:
- soma a multa de cada empréstimo, calculada como em `multaCentavos` (a carência e o teto de 5000 valem **por empréstimo**);
- o total nunca passa de 10000;
- lista vazia = 0;
- lista nula ou elemento negativo → exceção.

**Classes de equivalência**

| Entrada | Classe | Tipo | Teste |
|---|---|---|---|
| lista | C1 null | I | MT10 |
| | C2 vazia | V | MT01 |
| | C3 um elemento | V | MT02 |
| | C4 vários elementos | V | MT03 |
| | C5 contém elemento negativo | I | MT11 |
| regras por empréstimo | C6 um empréstimo passa do teto de 5000 | V | MT04 |
| | C7 professor com carência em cada empréstimo | V | MT05, MT06 |
| total | C8 abaixo de 10000 | V | MT07 |
| | C9 acima de 10000 (fica 10000) | V | MT09 |
| tipo | C10 null | I | MT12, MT13 |

**Valores-limite do total**

| Fronteira | Entrada | Esperado | Teste |
|---|---|---|---|
| tamanho da lista: 0 e 1 | `{}` / `{12}` | 0 / 1400 | MT01 / MT02 |
| logo abaixo do teto total | `{30, 29}` = 5000 + 4800 | 9800 | MT07 |
| exatamente no teto total | `{30, 30}` = 5000 + 5000 | 10000 | MT08 |
| logo acima do teto total | `{30, 30, 1}` = 10100 | 10000 | MT09 |
| carência do professor (por empréstimo) | `{3, 3}` / `{4, 4}` | 0 / 200 | MT06 / MT05 |

---

## Resumo

| Método | Casos de teste |
|---|---|
| prazoDias | 4 |
| limiteEmprestimos | 4 |
| dataDevolucao | 14 |
| multaCentavos | 24 |
| podeEmprestar | 17 |
| podeRenovar | 15 |
| multaTotalCentavos | 13 |
| ExemploTest (do kit) | 1 |
| **Total** | **92** |

Resultado da execução contra `lib/biblioteca-1.0.jar`: **52 passam e 40 falham**. As falhas estão em `DEFEITOS.md`.
