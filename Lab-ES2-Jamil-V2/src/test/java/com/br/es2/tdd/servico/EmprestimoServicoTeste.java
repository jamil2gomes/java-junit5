package com.br.es2.tdd.servico;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.br.ed2.tdd.modelo.Livro;
import com.br.ed2.tdd.modelo.Usuario;
import com.br.ed2.tdd.servico.EmprestimoServico;
import com.br.es2.tdd.builder.LivroBuilder;
import com.br.es2.tdd.builder.UsuarioBuilder;

public class EmprestimoServicoTeste {

	EmprestimoServico emprestimo = new EmprestimoServico();
	Usuario usuario1, usuario2;
	Livro livro1, livro2, livro3;

	@BeforeEach
	public void setUp() {

		usuario1 = UsuarioBuilder.umUsuario().comMatricula("123").comNome("Luana").constroi();

		usuario2 = UsuarioBuilder.umUsuario().comMatricula("456").comNome("Jamil").constroi();

		livro1 = LivroBuilder.umLivro().comAutor("J.k Rowling").comTitulo("Harry Potter 1").constroi();

		livro2 = LivroBuilder.umLivro().comAutor("J.k Rowling").comTitulo("Harry Potter 2").ehReservado(true).constroi();

		livro3 = LivroBuilder.umLivro().comAutor("J.k Rowling").comTitulo("Harry Potter 3").constroi();

	}

	@Test
	public void emprestimoEmLivroNaoReservado() {

		assertTrue(emprestimo.emprestar(usuario1, livro1));
	}

	@Test
	public void emprestimoEmLivroReservado() {
		RuntimeException exception = assertThrows(RuntimeException.class, () -> emprestimo.emprestar(usuario1, livro2),
				"Deveria ter lançado um RuntimeException");

		assertTrue(exception.getMessage().contains("Não pode emprestar livro reservado"));
	}

	@Test
	public void garantirQueDataPrevistaEstejaCorreta() {
		emprestimo.emprestar(usuario1, livro1);

		assertEquals(LocalDate.now().plusDays(7), emprestimo.getEmprestimo().getDataPrevista());
	}

	@Test
	public void testaUsuarioSemEmprestimo() {

		emprestimo.emprestar(usuario1, livro1);
		assertEquals(0, emprestimo.consultarEmprestimosPorUsuario(usuario2));

	}

	@Test
	public void testaUsuarioComUmEmprestimo() {

		emprestimo.emprestar(usuario1, livro1);

		assertEquals(1, emprestimo.consultarEmprestimosPorUsuario(usuario1));

	}

	@Test
	public void testaUsuarioComDoisEmprestimos() {

		emprestimo.emprestar(usuario1, livro1, livro3);
		emprestimo.emprestar(usuario1, livro1);

		assertEquals(2, emprestimo.consultarEmprestimosPorUsuario(usuario1));

	}

	@Test
	public void testaUsuarioComTresEmprestimos() {

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {

			emprestimo.emprestar(usuario1, livro1, livro3);
			emprestimo.emprestar(usuario1, livro1);
			emprestimo.emprestar(usuario1, livro3);
		}, "Deveria ter lançado um RuntimeException");

		assertTrue(exception.getMessage().contains("Usuario não pode ter 3 empréstimos"));

	}

	@Test
	public void testaDevolucaoAntesDaDataPrevista() {

		emprestimo.emprestar(usuario1, livro1);

		double valor = emprestimo.devolver(LocalDate.now().plusDays(1), livro1);

		LocalDate dataDevolucao = emprestimo.getEmprestimo().getDataDevolucao();
		LocalDate dataPrevista = emprestimo.getEmprestimo().getDataPrevista();
		assertAll(() -> assertTrue(dataDevolucao.isBefore(dataPrevista)), () -> assertEquals(5.0, valor, 0.00001));

	}

	@Test
	public void testaDevolucaoNaDataPrevista() {

		emprestimo.emprestar(usuario1, livro1);

		double valor = emprestimo.devolver(LocalDate.now().plusDays(7), livro1);

		LocalDate dataDevolucao = emprestimo.getEmprestimo().getDataDevolucao();
		LocalDate dataPrevista = emprestimo.getEmprestimo().getDataPrevista();
		assertAll(() -> assertTrue(dataDevolucao.isEqual(dataPrevista)), () -> assertEquals(5.0, valor, 0.00001));

	}

	@Test
	public void testaDevolucaoUmDiaDaDataPrevista() {

		emprestimo.emprestar(usuario1, livro1);

		double valor = emprestimo.devolver(LocalDate.now().plusDays(8), livro1);

		LocalDate dataDevolucao = emprestimo.getEmprestimo().getDataDevolucao();
		LocalDate dataPrevista = emprestimo.getEmprestimo().getDataPrevista();
		assertAll(() -> assertEquals(1, ChronoUnit.DAYS.between(dataPrevista, dataDevolucao)),
				() -> assertEquals(5.4, valor, 0.00001));

	}

	@Test
	public void testaDevolucaoTrintaDiasDepoisDaDataPrevista() {

		emprestimo.emprestar(usuario1, livro1);

		double valor = emprestimo.devolver(LocalDate.now().plusDays(37), livro1);

		LocalDate dataDevolucao = emprestimo.getEmprestimo().getDataDevolucao();
		LocalDate dataPrevista = emprestimo.getEmprestimo().getDataPrevista();
		assertAll(() -> assertEquals(30, ChronoUnit.DAYS.between(dataPrevista, dataDevolucao)),
				() -> assertEquals(8.0, valor, 0.00001));

	}

}
