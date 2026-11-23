package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * limiteEmprestimos(tipo) — Anexo A: 3 para aluno, 8 para professor, 1 para visitante.
 * Classes de equivalência: uma classe válida por tipo e uma classe inválida (tipo nulo).
 */
class LimiteEmprestimosTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    @Test
    void LE01_alunoPodeTer3() {
        assertEquals(3, regras.limiteEmprestimos(TipoUsuario.ALUNO));
    }

    @Test
    void LE02_professorPodeTer8() {
        assertEquals(8, regras.limiteEmprestimos(TipoUsuario.PROFESSOR));
    }

    @Test
    void LE03_visitantePodeTer1() {
        assertEquals(1, regras.limiteEmprestimos(TipoUsuario.VISITANTE));
    }

    @Test
    void LE04_tipoNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> regras.limiteEmprestimos(null));
    }
}
