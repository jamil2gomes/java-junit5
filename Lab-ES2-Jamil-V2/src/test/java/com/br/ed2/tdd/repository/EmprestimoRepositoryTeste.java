package com.br.ed2.tdd.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.br.ed2.tdd.modelo.Emprestimo;
import com.br.ed2.tdd.modelo.Livro;
import com.br.ed2.tdd.modelo.Usuario;
import com.br.ed2.tdd.servico.EmprestimoServico;
import com.br.es2.tdd.builder.LivroBuilder;
import com.br.es2.tdd.builder.UsuarioBuilder;

public class EmprestimoRepositoryTeste {

	private EntityManager manager;
	private static EntityManagerFactory emf;
	private EmprestimoRepository empRepo;

	@BeforeAll
	public static void inicio() {
		emf = Persistence.createEntityManagerFactory("locadoraDev");
	}

	@BeforeEach
	public void antes() {
		manager = emf.createEntityManager();
		manager.getTransaction().begin();
		empRepo = new EmprestimoRepositoryImplement(manager);
	}

	@AfterEach
	public void depois() {
		manager.getTransaction().rollback();
	}

	@AfterAll
	public static void fim() {
		emf.close();
	}

	@Test
	public void deveSalvarEmprestimo() {

		Usuario usuario = UsuarioBuilder.umUsuario().comNome("user1").comMatricula("123").constroi();

		Livro livro = LivroBuilder.umLivro().comAutor("autor 1").comTitulo("titulo 1").constroi();
		
		Livro livro2 = LivroBuilder.umLivro().comAutor("autor 1").comTitulo("titulo 1").constroi();

		EmprestimoServico emp = new EmprestimoServico(empRepo);
		boolean res = emp.emprestar(usuario, livro, livro2);
		manager.flush();
		manager.clear();

		assertTrue(res);

	}

	@Test
	public void deveRetornarListaDeLivroEmAtraso() {

		Livro livro1 = LivroBuilder.umLivro().comAutor("livro 1").comTitulo("livro 11").constroi();

		Usuario usuario = UsuarioBuilder.umUsuario().comNome("user1").comMatricula("123").constroi();

		EmprestimoServico emp = new EmprestimoServico(empRepo);
		emp.emprestar(usuario, livro1);
		
		
		Emprestimo emprestimoBuscado = empRepo.buscaEmprestimoPor(usuario.getNome(), livro1.getTitulo());
		emprestimoBuscado.setDataPrevista(LocalDate.now().minusDays(4));
		
		empRepo.atualiza(emprestimoBuscado);
		
		
		List<Livro> livrosAtrasados = empRepo.listaDeLivrosEmAtraso();
		
		manager.flush();
		manager.clear();

		assertEquals(1, livrosAtrasados.size());

	}

}
