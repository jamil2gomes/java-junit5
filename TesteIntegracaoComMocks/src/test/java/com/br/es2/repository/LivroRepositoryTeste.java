package com.br.es2.repository;

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

import com.br.es2.builder.EmprestimoBuilder;
import com.br.es2.builder.LivroBuilder;
import com.br.es2.builder.UsuarioBuilder;

import br.com.es2.model.Emprestimo;
import br.com.es2.model.Livro;
import br.com.es2.model.Usuario;
import br.com.es2.repository.EmprestimoRepository;
import br.com.es2.repository.EmprestimoRepositoryImplementacao;
import br.com.es2.repository.LivroRepository;
import br.com.es2.repository.LivroRepositoryImplementacao;

public class LivroRepositoryTeste {

	private EntityManager manager;
	private static EntityManagerFactory emf;

	private Livro livro;
	private Usuario usuario;
	private EmprestimoRepository emprestimoRepo;
	private LivroRepository livroRepository;

	@BeforeAll
	public static void inicio() {
		emf = Persistence.createEntityManagerFactory("locadoraDev");
	}

	@BeforeEach
	public void antes() {
		manager = emf.createEntityManager();
		manager.getTransaction().begin();
		livroRepository = new LivroRepositoryImplementacao(manager);
		emprestimoRepo = new EmprestimoRepositoryImplementacao(manager);
		livro = LivroBuilder.umLivro().comAutor("Autor X").comTitulo("Titulo Y").constroi();
		usuario = UsuarioBuilder.umUsuario().comMatricula("123").comNome("Jamil").constroi();

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

		livroRepository.salva(livro);

		manager.flush();
		manager.clear();

		Livro livro = livroRepository.buscaPor(3L);

		assertEquals("Titulo Y", livro.getTitulo());

	}

	@Test
	public void deveAtualizarLivro() {

		livroRepository.salva(livro);

		manager.flush();
		manager.clear();

		Livro livroBuscado = livroRepository.buscaPor(2L);
		livroBuscado.setTitulo("Titulo Atualizado");

		Livro livroAtualizado = livroRepository.atualiza(livroBuscado);

		assertEquals("Titulo Atualizado", livroAtualizado.getTitulo());
	}

	@Test
	public void deveBuscarHistoricoDeLivro() {

		Emprestimo emprestimo = EmprestimoBuilder.umEmprestimo().comUsuario(usuario).comLivro(livro).constroi();

		emprestimoRepo.salva(emprestimo);
		manager.flush();
		manager.clear();

		List<Emprestimo> emprestimos = livroRepository.historicoDeEmprestimosDo(livro);

		assertEquals(1, emprestimos.size());
	}

}
