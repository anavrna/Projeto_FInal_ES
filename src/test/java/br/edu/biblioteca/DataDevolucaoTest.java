package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;

/**
 * dataDevolucao(retirada, tipo) — Anexo A: soma o prazo do tipo à data de retirada.
 * Se o resultado cair num domingo, passa para a segunda-feira seguinte. Sábado é dia normal.
 *
 * Classes de equivalência do dia em que o resultado cai:
 *   - dia útil (segunda a sexta): devolve a própria data
 *   - sábado: devolve a própria data
 *   - domingo: devolve a segunda-feira seguinte
 * Valores-limite: sexta, sábado e domingo (a fronteira entre "fica" e "pula").
 * Também variamos o dia da RETIRADA (inclusive domingo), pois a regra olha só o dia do resultado.
 * Classes inválidas: retirada nula, tipo nulo.
 *
 * Datas de referência: 21/11/2026 é sábado, 22/11/2026 é domingo, 23/11/2026 é segunda.
 */
class DataDevolucaoTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    @ParameterizedTest(name = "{0}: retirada {1} -> devolucao {2}")
    @CsvSource({
        // tipo,       retirada,     esperado       // dia em que retirada + prazo cai
        "ALUNO,      2026-11-23, 2026-12-07",       // DD01 segunda (dia útil)
        "ALUNO,      2026-11-20, 2026-12-04",       // DD02 sexta (limite antes do sábado)
        "ALUNO,      2026-11-21, 2026-12-05",       // DD03 sábado: fica no sábado
        "ALUNO,      2026-11-22, 2026-12-07",       // DD04 domingo: passa para segunda
        "PROFESSOR,  2026-11-24, 2026-12-24",       // DD05 quinta (dia útil)
        "PROFESSOR,  2026-11-05, 2026-12-05",       // DD06 sábado: fica no sábado
        "PROFESSOR,  2026-11-06, 2026-12-07",       // DD07 domingo: passa para segunda
        "PROFESSOR,  2026-11-22, 2026-12-22",       // DD08 retirada num domingo, resultado terça:
                                                     //      o domingo que conta é o do RESULTADO
        "VISITANTE,  2026-11-23, 2026-11-30",       // DD09 segunda (dia útil)
        "VISITANTE,  2026-11-21, 2026-11-28",       // DD10 sábado: fica no sábado
        "VISITANTE,  2026-11-22, 2026-11-30",       // DD11 domingo: passa para segunda
        "VISITANTE,  2026-12-27, 2027-01-04"        // DD12 domingo na virada do ano
    })
    void DD01a12_somaPrazoEPulaDomingo(TipoUsuario tipo, LocalDate retirada, LocalDate esperado) {
        assertEquals(esperado, regras.dataDevolucao(retirada, tipo));
    }

    @Test
    void DD13_retiradaNulaLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> regras.dataDevolucao(null, TipoUsuario.ALUNO));
    }

    @Test
    void DD14_tipoNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> regras.dataDevolucao(LocalDate.of(2026, 11, 23), null));
    }
}
