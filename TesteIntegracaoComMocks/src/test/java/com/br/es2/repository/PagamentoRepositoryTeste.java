package com.br.es2.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import br.com.es2.model.Pagamento;
import br.com.es2.model.Usuario;
import br.com.es2.repository.EmprestimoRepository;
import br.com.es2.repository.EmprestimoRepositoryImplementacao;
import br.com.es2.repository.PagamentoRepository;
import br.com.es2.repository.PagamentoRepositoryImplementacao;
import br.com.es2.service.EmprestimoService;

public class PagamentoRepositoryTeste {
	private EntityManager manager;
	private static EntityManagerFactory emf;

	private Livro livro;
	private Usuario usuario;
	private PagamentoRepository pagamentoRepo;

	@BeforeAll
	public static void inicio() {
		emf = Persistence.createEntityManagerFactory("locadoraDev");
	}

	@BeforeEach
	public void antes() {
		manager = emf.createEntityManager();
		manager.getTransaction().begin();
		pagamentoRepo = new PagamentoRepositoryImplementacao(manager);
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
	public void deveSalvarPagamento() {

		Emprestimo emprestimo = EmprestimoBuilder.umEmprestimo().comUsuario(usuario).comLivro(livro).constroi();

		Pagamento pagamento = new Pagamento(5.0, emprestimo.getUsuario(), emprestimo);

		pagamentoRepo.salva(pagamento);

		Pagamento pagamentoBuscado = pagamentoRepo.buscaPor(1L);

		assertAll(  () -> assertNotNull(pagamentoBuscado),
					() -> assertEquals(1L, pagamentoBuscado.getId()),
					() -> assertEquals(1L, pagamentoBuscado.getUsuario().getId()),
					() -> assertEquals(1L, pagamentoBuscado.getEmprestimo().getId())
				);

	}
	
}
