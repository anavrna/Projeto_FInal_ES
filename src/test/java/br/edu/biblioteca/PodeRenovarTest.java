package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * podeRenovar(tipo, renovacoesFeitas, possuiReserva, emAtraso) — Anexo A:
 *   - visitante nunca renova
 *   - aluno renova até 2 vezes, professor até 3 vezes
 *   - em qualquer caso, só renova se NÃO houver reserva e NÃO estiver em atraso
 *   - renovacoesFeitas negativo: IllegalArgumentException, para qualquer tipo
 *
 * Interpretação de "até 2 vezes": o total de renovações não passa de 2.
 * Logo, com 0 ou 1 renovação feita o aluno ainda pode renovar; com 2 feitas, não pode mais.
 * O mesmo raciocínio vale para o professor com 3.
 */
class PodeRenovarTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    @ParameterizedTest(name = "{0} com {1} renovacoes, reserva {2}, atraso {3} -> {4}")
    @CsvSource({
        // tipo,     feitas, reserva, atraso, esperado
        "ALUNO,      0,      false,   false,  true",    // PR01 limite: nenhuma renovação
        "ALUNO,      1,      false,   false,  true",    // PR02 limite: máximo - 1
        "ALUNO,      2,      false,   false,  false",   // PR03 limite: atingiu o máximo
        "PROFESSOR,  0,      false,   false,  true",    // PR04 limite: nenhuma renovação
        "PROFESSOR,  2,      false,   false,  true",    // PR05 limite: máximo - 1
        "PROFESSOR,  3,      false,   false,  false",   // PR06 limite: atingiu o máximo
        "VISITANTE,  0,      false,   false,  false",   // PR07 visitante nunca renova
        "ALUNO,      0,      true,    false,  false",   // PR08 obra com reserva
        "PROFESSOR,  0,      true,    false,  false",   // PR09 obra com reserva
        "ALUNO,      0,      false,   true,   false",   // PR10 empréstimo em atraso
        "PROFESSOR,  0,      false,   true,   false"    // PR11 empréstimo em atraso
    })
    void PR01a11_verificaRegrasDeRenovacao(TipoUsuario tipo, int feitas, boolean reserva,
                                           boolean atraso, boolean esperado) {
        assertEquals(esperado, regras.podeRenovar(tipo, feitas, reserva, atraso));
    }

    @ParameterizedTest(name = "{0} com -1 renovacoes lanca excecao")
    @CsvSource({
        "ALUNO",       // PR12
        "PROFESSOR",   // PR13
        "VISITANTE"    // PR14 a especificação diz "para qualquer tipo", inclusive visitante
    })
    void PR12a14_renovacoesNegativasLancamExcecao(TipoUsuario tipo) {
        assertThrows(IllegalArgumentException.class,
                () -> regras.podeRenovar(tipo, -1, false, false));
    }

    @Test
    void PR15_tipoNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> regras.podeRenovar(null, 0, false, false));
    }
}
