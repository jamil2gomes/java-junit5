package com.br.ed2.tdd.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

public class LivroRepositoryTeste {

	private EntityManager manager;
	private static EntityManagerFactory emf;
	private LivroRepository livroRepo;
	private UsuarioRepository usuarioRepo;
	private EmprestimoRepository empRepo;

	@BeforeAll
	public static void inicio() {
		emf = Persistence.createEntityManagerFactory("locadoraDev");
	}

	@BeforeEach
	public void antes() {
		manager = emf.createEntityManager();
		manager.getTransaction().begin();
		livroRepo = new LivroRepositoryImplement(manager);
		usuarioRepo = new UsuarioRepositoryImplement(manager);
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
	public void deveSalvarLivro() {

		Livro livro = LivroBuilder.umLivro().comTitulo("Livro 1").comAutor("Autor 1").constroi();

		String resultado = livroRepo.salva(livro);

		assertEquals("Livro salvo com sucesso!", resultado);
	}

	@Test
	public void deveAtualizarLivro() {

		Livro livro = LivroBuilder.umLivro().comTitulo("Livro 1").comAutor("Autor 1").constroi();

		livroRepo.salva(livro);
		manager.flush();
		manager.clear();

		Livro livroBuscado = livroRepo.buscaPor(livro);

		livroBuscado.setTitulo("Livro Atualizado");

		Livro livroAtualizado = livroRepo.atualiza(livroBuscado);

		assertEquals("Livro Atualizado", livroAtualizado.getTitulo());

	}

	@Test
	public void deveBuscarHistoricoDeEmprestimoDoLivro() {

		Livro livro1 = LivroBuilder.umLivro().comTitulo("Livro 2").constroi();
		livroRepo.salva(livro1);

		Usuario usu1 = UsuarioBuilder.umUsuario().comNome("User 1").constroi();
		usuarioRepo.salvar(usu1);

		EmprestimoServico emp = new EmprestimoServico();
		emp.emprestar(usu1, livro1);

		empRepo.salva(emp.getEmprestimo());

		EmprestimoServico emp2 = new EmprestimoServico();
		emp2.emprestar(usu1, livro1);

		empRepo.salva(emp2.getEmprestimo());
		manager.flush();
		manager.clear();

		List<Emprestimo> historico = livroRepo.historicoDeEmprestimosDo(livro1);

		assertEquals(2, historico.size());

	}

	@Test
	public void deveRetornarLivrosEmprestados() {

		Livro livro1 = LivroBuilder.umLivro().comTitulo("Livro 2").isEmprestado(true).constroi();
		Livro livro2 = LivroBuilder.umLivro().comTitulo("Livro 3").isEmprestado(true).constroi();
		livroRepo.salva(livro1);
		livroRepo.salva(livro2);
		manager.flush();
		manager.clear();

		List<Livro> livros = livroRepo.listaLivrosEmprestados();

		assertEquals(2, livros.size());

	}

}
