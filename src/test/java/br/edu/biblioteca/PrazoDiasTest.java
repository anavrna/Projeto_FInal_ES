package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * prazoDias(tipo) — Anexo A: 14 dias para aluno, 30 para professor, 7 para visitante.
 * Classes de equivalência: uma classe válida por tipo (cada tipo tem uma saída diferente)
 * e uma classe inválida (tipo nulo).
 */
class PrazoDiasTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    @Test
    void PD01_alunoTem14Dias() {
        assertEquals(14, regras.prazoDias(TipoUsuario.ALUNO));
    }

    @Test
    void PD02_professorTem30Dias() {
        assertEquals(30, regras.prazoDias(TipoUsuario.PROFESSOR));
    }

    @Test
    void PD03_visitanteTem7Dias() {
        assertEquals(7, regras.prazoDias(TipoUsuario.VISITANTE));
    }

    @Test
    void PD04_tipoNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> regras.prazoDias(null));
    }
}
