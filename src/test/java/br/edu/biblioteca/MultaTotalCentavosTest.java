package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * multaTotalCentavos(tipo, diasAtrasoPorEmprestimo) — Anexo A:
 *   - soma a multa de cada empréstimo, cada uma calculada como em multaCentavos
 *     (ou seja: carência do professor e teto de 5000 valem POR empréstimo)
 *   - o total nunca passa de 10000
 *   - lista vazia: 0
 *   - lista nula ou elemento negativo: IllegalArgumentException
 *
 * Classes de equivalência da lista: nula | vazia | 1 elemento | vários elementos |
 * contém elemento negativo. Classes do total: abaixo do teto | igual ao teto | acima do teto.
 */
class MultaTotalCentavosTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    @Test
    void MT01_listaVaziaDaZero() {
        assertEquals(0, regras.multaTotalCentavos(TipoUsuario.ALUNO, new int[] {}));
    }

    @Test
    void MT02_umEmprestimoIgualAMultaCentavos() {
        // 12 dias de aluno = 1400 (exemplo do Anexo A)
        assertEquals(1400, regras.multaTotalCentavos(TipoUsuario.ALUNO, new int[] {12}));
    }

    @Test
    void MT03_variosEmprestimosSomam() {
        // 5 dias = 500, 12 dias = 1400, 0 dias = 0 -> 1900
        assertEquals(1900, regras.multaTotalCentavos(TipoUsuario.ALUNO, new int[] {5, 12, 0}));
    }

    @Test
    void MT04_tetoDeCadaEmprestimoAntesDeSomar() {
        // 40 dias sozinho seria 7000, mas o teto por empréstimo é 5000; 1 dia = 100 -> 5100
        assertEquals(5100, regras.multaTotalCentavos(TipoUsuario.ALUNO, new int[] {40, 1}));
    }

    @Test
    void MT05_carenciaDoProfessorEmCadaEmprestimo() {
        // cada empréstimo de 4 dias: 4 - 3 = 1 dia cobrado = 100 -> total 200
        assertEquals(200, regras.multaTotalCentavos(TipoUsuario.PROFESSOR, new int[] {4, 4}));
    }

    @Test
    void MT06_professorDentroDaCarenciaDaZero() {
        assertEquals(0, regras.multaTotalCentavos(TipoUsuario.PROFESSOR, new int[] {3, 3}));
    }

    @Test
    void MT07_limiteLogoAbaixoDoTetoTotal() {
        // 30 dias = 5000, 29 dias = 4800 -> 9800
        assertEquals(9800, regras.multaTotalCentavos(TipoUsuario.ALUNO, new int[] {30, 29}));
    }

    @Test
    void MT08_limiteExatamenteNoTetoTotal() {
        // 5000 + 5000 = 10000
        assertEquals(10000, regras.multaTotalCentavos(TipoUsuario.ALUNO, new int[] {30, 30}));
    }

    @Test
    void MT09_limiteLogoAcimaDoTetoTotal() {
        // 5000 + 5000 + 100 = 10100, mas o teto total é 10000
        assertEquals(10000, regras.multaTotalCentavos(TipoUsuario.ALUNO, new int[] {30, 30, 1}));
    }

    @Test
    void MT10_listaNulaLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> regras.multaTotalCentavos(TipoUsuario.ALUNO, null));
    }

    @Test
    void MT11_elementoNegativoLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> regras.multaTotalCentavos(TipoUsuario.ALUNO, new int[] {5, -1}));
    }

    @Test
    void MT12_tipoNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> regras.multaTotalCentavos(null, new int[] {5}));
    }

    @Test
    void MT13_tipoNuloComListaVaziaLancaExcecao() {
        // "Em todos os métodos, tipo nulo lança IllegalArgumentException", mesmo sem empréstimos
        assertThrows(IllegalArgumentException.class,
                () -> regras.multaTotalCentavos(null, new int[] {}));
    }
}
