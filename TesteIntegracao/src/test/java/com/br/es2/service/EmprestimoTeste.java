package com.br.es2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.br.es2.builder.LivroBuilder;
import com.br.es2.builder.UsuarioBuilder;

import br.com.es2.model.Emprestimo;
import br.com.es2.model.Livro;
import br.com.es2.model.Usuario;
import br.com.es2.repository.EmprestimoRepository;
import br.com.es2.service.EmprestimoService;

public class EmprestimoTeste {

	private EmprestimoService servico;
	private EmprestimoRepository repositorio;
	private Usuario joao;
	private Livro livro1, livro2, livro3;

	@BeforeEach
	public void setUp() {

		joao = UsuarioBuilder.umUsuario().comNome("Joao").comMatricula("123").constroi();
		livro1 = LivroBuilder.umLivro().comAutor("Autor 1").comTitulo("Livro 1").ehReservado(false).constroi();
		livro2 = LivroBuilder.umLivro().comAutor("Autor 2").comTitulo("Livro 2").ehReservado(false).constroi();
		livro3 = LivroBuilder.umLivro().comAutor("Autor 3").comTitulo("Livro 3").ehReservado(false).constroi();
		repositorio = Mockito.mock(EmprestimoRepository.class);
		servico = new EmprestimoService();
		servico.setRepositorio(repositorio);

	}

	@Test
	public void deveRealizarEmprestimoComLivroNaoReservado() {

		List<Emprestimo> emprestimosRealizados = servico.realizarEmprestimo(joao, livro1);
		
		Mockito.verify(repositorio,  Mockito.times(1)).salva(emprestimosRealizados.get(0));

		assertEquals(1, emprestimosRealizados.size());

	}

	@Test
	public void testarEmprestimoComLivroReservado() {

		
		livro1.setReservado(true);

		 assertThrows(RuntimeException.class,
				 	  () -> servico.realizarEmprestimo(joao, livro1),
				 	 "Deveria ter lançado um IllegalArgumentException");

	}

	@Test
	public void deveVerificarDataPrevistaCorretamente() {
		LocalDate dataPrevista = null;

		List<Emprestimo> emprestimosRealizados = servico.realizarEmprestimo(joao, livro1);

		Mockito.verify(repositorio, Mockito.times(1)).salva(emprestimosRealizados.get(0));

		dataPrevista = emprestimosRealizados.get(0).getDataPrevista();

		assertEquals(LocalDate.now().plusDays(7), dataPrevista);

	}

	@Test
	public void testarUsuarioSemEmprestimo() {

		Usuario ana = UsuarioBuilder.umUsuario().comMatricula("12345").comNome("Ana").constroi();

		int totalEmprestimo = servico.buscarEmprestimosDo(ana);

		assertEquals(0, totalEmprestimo);
	}

	@Test
	public void testarUsuarioComUmEmprestimo() {

		List<Emprestimo> emprestimosRealizados = servico.realizarEmprestimo(joao, livro1);
		
		Mockito.verify(repositorio,  Mockito.times(1)).salva(emprestimosRealizados.get(0));

		int totalEmprestimo = servico.buscarEmprestimosDo(joao);
		assertEquals(1, totalEmprestimo);
	}

	@Test
	public void testarUsuarioComDoisEmprestimo() {

		List<Emprestimo> emprestimosRealizados = servico.realizarEmprestimo(joao, livro1, livro2);
		
		for(int i = 0; i < emprestimosRealizados.size(); i++)
			Mockito.verify(repositorio,  Mockito.times(2)).salva(emprestimosRealizados.get(i));

		int totalEmprestimo = servico.buscarEmprestimosDo(joao);
		assertEquals(2, totalEmprestimo);
	}

	@Test
	public void tentativaDeUsuarioComTresEmprestimo() {

		
		
RuntimeException e = 
				assertThrows(RuntimeException.class,
					 () ->{ 
						servico.realizarEmprestimo(joao, livro1);
						servico.realizarEmprestimo(joao, livro2);
						servico.realizarEmprestimo(joao, livro3);
						servico.realizarEmprestimo(joao, livro3);
						},
					 "Deveria ter lançado um IllegalArgumentException");

		assertEquals("Usuario não pode ter mais de 3 emprestimos em aberto", e.getMessage());

	}

	@Test
	public void devolucaoDeEmprestimoAntesDaDataPrevista() {

		List<Emprestimo> emprestimosRealizados = servico.realizarEmprestimo(joao, livro1);

		double total = 0.0;

		total += servico.realizaDevolucao(emprestimosRealizados.get(0), LocalDate.now());

		assertEquals(5.0, total, 0.00001);
		assertFalse(livro1.isEmprestado());

		Mockito.verify(repositorio, Mockito.times(1)).atualiza(emprestimosRealizados.get(0));

	}

	@Test
	public void devolucaoDeEmprestimoNaDaDataPrevista() {

		List<Emprestimo> emprestimosRealizados = servico.realizarEmprestimo(joao, livro1);

		double total = 0.0;

		total += servico.realizaDevolucao(emprestimosRealizados.get(0), emprestimosRealizados.get(0).getDataPrevista());

		assertEquals(5.0, total, 0.00001);
		assertFalse(livro1.isEmprestado());

		Mockito.verify(repositorio, Mockito.times(1)).atualiza(emprestimosRealizados.get(0));

	}

	@Test
	public void devolucaoDeEmprestimoUmDiaDaDataPrevista() {

		List<Emprestimo> emprestimosRealizados = servico.realizarEmprestimo(joao, livro1);

		double total = 0.0;

		total += servico.realizaDevolucao(emprestimosRealizados.get(0),
				emprestimosRealizados.get(0).getDataPrevista().plusDays(1));

		assertEquals(5.4, total, 0.00001);
		assertFalse(livro1.isEmprestado());

		Mockito.verify(repositorio, Mockito.times(1)).atualiza(emprestimosRealizados.get(0));

	}

	@Test
	public void devolucaoDeEmprestimoComTrintaDiasAposDataPrevista() {

		List<Emprestimo> emprestimosRealizados = servico.realizarEmprestimo(joao, livro1);

		double total = 0.0;

		total += servico.realizaDevolucao(emprestimosRealizados.get(0),
				emprestimosRealizados.get(0).getDataPrevista().plusDays(30));

		assertEquals(8.0, total, 0.00001);
		assertFalse(livro1.isEmprestado());

		Mockito.verify(repositorio, Mockito.times(1)).atualiza(emprestimosRealizados.get(0));

	}

}
