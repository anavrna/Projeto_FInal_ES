package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * podeEmprestar(tipo, emprestimosAtivos, multaPendenteCentavos, possuiAtraso) — Anexo A:
 * true somente se as três condições valerem juntas:
 *   (a) emprestimosAtivos < limite do tipo (aluno 3, professor 8, visitante 1)
 *   (b) multaPendenteCentavos <= 1000
 *   (c) possuiAtraso == false
 * Ativos ou multa negativos: IllegalArgumentException.
 *
 * Técnica: em cada teste só UMA condição fica no limite; as outras ficam num valor válido
 * "seguro" (multa 0, sem atraso, poucos empréstimos). Assim, se o teste falhar, sabemos
 * qual condição causou a falha.
 */
class PodeEmprestarTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    @ParameterizedTest(name = "{0} com {1} ativos, multa {2}, atraso {3} -> {4}")
    @CsvSource({
        // tipo,     ativos, multa, atraso, esperado
        "ALUNO,      0,      0,     false,  true",    // PE01 limite: zero empréstimos
        "ALUNO,      2,      0,     false,  true",    // PE02 limite: limite - 1
        "ALUNO,      3,      0,     false,  false",   // PE03 limite: igual ao limite
        "PROFESSOR,  7,      0,     false,  true",    // PE04 limite: limite - 1
        "PROFESSOR,  8,      0,     false,  false",   // PE05 limite: igual ao limite
        "VISITANTE,  0,      0,     false,  true",    // PE06 limite: limite - 1
        "VISITANTE,  1,      0,     false,  false",   // PE07 limite: igual ao limite
        "ALUNO,      0,      1000,  false,  true",    // PE08 limite: multa exatamente R$ 10,00
        "ALUNO,      0,      1001,  false,  false",   // PE09 limite: multa 1 centavo acima
        "ALUNO,      0,      999,   false,  true",    // PE10 limite: multa 1 centavo abaixo
        "PROFESSOR,  0,      1001,  false,  false",   // PE11 multa acima, outro tipo
        "ALUNO,      0,      0,     true,   false",   // PE12 tem atraso
        "PROFESSOR,  0,      0,     true,   false",   // PE13 tem atraso, outro tipo
        "VISITANTE,  0,      0,     true,   false"    // PE14 tem atraso, outro tipo
    })
    void PE01a14_verificaAsTresCondicoes(TipoUsuario tipo, int ativos, int multa,
                                         boolean atraso, boolean esperado) {
        assertEquals(esperado, regras.podeEmprestar(tipo, ativos, multa, atraso));
    }

    @Test
    void PE15_emprestimosAtivosNegativoLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> regras.podeEmprestar(TipoUsuario.ALUNO, -1, 0, false));
    }

    @Test
    void PE16_multaNegativaLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> regras.podeEmprestar(TipoUsuario.ALUNO, 0, -1, false));
    }

    @Test
    void PE17_tipoNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> regras.podeEmprestar(null, 0, 0, false));
    }
}
