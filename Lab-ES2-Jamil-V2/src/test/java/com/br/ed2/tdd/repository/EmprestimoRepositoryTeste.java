package com.br.ed2.tdd.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

		EmprestimoServico emp = new EmprestimoServico();
		emp.emprestar(usuario, livro);
		manager.flush();
		manager.clear();

		String res = empRepo.salva(emp.getEmprestimo());

		assertEquals("Emprestimo salvo com sucesso!", res);

	}

	@Test
	public void deveRetornarListaDeLivroEmAtraso() {

		Livro livro1 = LivroBuilder.umLivro().comAutor("1").comTitulo("11").constroi();
		Livro livro2 = LivroBuilder.umLivro().comAutor("2").comTitulo("22").constroi();

		Usuario usuario = UsuarioBuilder.umUsuario().comNome("user1").comMatricula("123").constroi();

		Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("user1").comMatricula("123").constroi();

		EmprestimoServico emp = new EmprestimoServico();
		emp.emprestar(usuario, livro1);

		emp.getEmprestimo().setDataPrevista(LocalDate.now().minusDays(4));

		EmprestimoServico emp1 = new EmprestimoServico();
		emp1.emprestar(usuario2, livro2);

		emp1.getEmprestimo().setDataPrevista(LocalDate.now().minusDays(3));

		empRepo.salva(emp.getEmprestimo());

		empRepo.salva(emp1.getEmprestimo());
		manager.flush();
		manager.clear();

		List<Livro> livrosAtrasados = empRepo.listaDeLivrosEmAtraso();

		assertEquals(2, livrosAtrasados.size());

	}

}
