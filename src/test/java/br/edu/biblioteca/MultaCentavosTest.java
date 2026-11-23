package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * multaCentavos(tipo, diasAtraso) — Anexo A:
 *   - professor: os 3 primeiros dias não são cobrados (carência); demais tipos: todos cobrados
 *   - do 1º ao 10º dia cobrado: 100 por dia; do 11º em diante: 200 por dia
 *   - teto de 5000 por empréstimo
 *   - dias negativos: IllegalArgumentException
 *
 * Faixas de dias COBRADOS (classes de equivalência):
 *   0 | 1..10 (100/dia) | 11..30 (200/dia, ainda abaixo do teto) | 31+ (teto 5000)
 * Para aluno/visitante, dias cobrados = dias de atraso.
 * Para professor, dias cobrados = dias de atraso - 3 (nunca menos que 0).
 * Conta do teto: 10 x 100 + 20 x 200 = 5000, então 30 dias cobrados já atingem o teto.
 */
class MultaCentavosTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    @ParameterizedTest(name = "{0} com {1} dias de atraso -> {2}")
    @CsvSource({
        // tipo,     dias, esperado
        "ALUNO,      0,    0",       // MC01 limite: sem atraso
        "ALUNO,      1,    100",     // MC02 limite: primeiro dia cobrado
        "ALUNO,      5,    500",     // MC03 representante da faixa 1..10
        "ALUNO,      10,   1000",    // MC04 limite: último dia a R$ 1,00
        "ALUNO,      11,   1200",    // MC05 limite: primeiro dia a R$ 2,00
        "ALUNO,      12,   1400",    // MC06 exemplo do próprio Anexo A
        "ALUNO,      29,   4800",    // MC07 limite: logo abaixo do teto
        "ALUNO,      30,   5000",    // MC08 limite: atinge o teto exatamente
        "ALUNO,      31,   5000",    // MC09 limite: passaria do teto
        "ALUNO,      100,  5000",    // MC10 representante da faixa acima do teto
        "VISITANTE,  1,    100",     // MC11 visitante não tem carência
        "VISITANTE,  11,   1200",    // MC12 visitante: primeiro dia a R$ 2,00
        "VISITANTE,  31,   5000",    // MC13 visitante: teto
        "PROFESSOR,  0,    0",       // MC14 professor sem atraso
        "PROFESSOR,  3,    0",       // MC15 limite: último dia de carência
        "PROFESSOR,  4,    100",     // MC16 limite: primeiro dia cobrado
        "PROFESSOR,  13,   1000",    // MC17 limite: 10º dia cobrado
        "PROFESSOR,  14,   1200",    // MC18 limite: 11º dia cobrado
        "PROFESSOR,  33,   5000",    // MC19 limite: atinge o teto
        "PROFESSOR,  34,   5000"     // MC20 limite: passaria do teto
    })
    void MC01a20_calculaMulta(TipoUsuario tipo, int dias, int esperado) {
        assertEquals(esperado, regras.multaCentavos(tipo, dias));
    }

    @ParameterizedTest(name = "{0} com -1 dia lanca excecao")
    @CsvSource({
        "ALUNO",       // MC21
        "PROFESSOR",   // MC22 (a carência não pode "esconder" o valor negativo)
        "VISITANTE"    // MC23
    })
    void MC21a23_diasNegativosLancamExcecao(TipoUsuario tipo) {
        assertThrows(IllegalArgumentException.class, () -> regras.multaCentavos(tipo, -1));
    }

    @Test
    void MC24_tipoNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> regras.multaCentavos(null, 5));
    }
}
