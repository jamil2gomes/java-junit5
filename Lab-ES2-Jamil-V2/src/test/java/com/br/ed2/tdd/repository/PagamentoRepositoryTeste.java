package com.br.ed2.tdd.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.br.ed2.tdd.modelo.Livro;
import com.br.ed2.tdd.modelo.Pagamento;
import com.br.ed2.tdd.modelo.Usuario;
import com.br.ed2.tdd.servico.EmprestimoServico;
import com.br.es2.tdd.builder.LivroBuilder;
import com.br.es2.tdd.builder.UsuarioBuilder;

public class PagamentoRepositoryTeste {

	private EntityManager manager;
	private static EntityManagerFactory emf;
	private PagamentoRepository pagRepo;
	private EmprestimoRepository empRepo;

	@BeforeAll
	public static void inicio() {
		emf = Persistence.createEntityManagerFactory("locadoraDev");
	}

	@BeforeEach
	public void antes() {
		manager = emf.createEntityManager();
		manager.getTransaction().begin();
		pagRepo = new PagamentoRepositorioImplement(manager);
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
	public void deveRealizarPagamento() {

		Usuario usuario1 = UsuarioBuilder.umUsuario().comNome("usuario1").comMatricula("123").constroi();
		Livro livro = LivroBuilder.umLivro().comAutor("autor 1").comTitulo("titulo 1").constroi();

		EmprestimoServico emp = new EmprestimoServico(empRepo);
		emp.emprestar(usuario1, livro);
	
	

		Double valorAPagar = emp.devolver(usuario1, LocalDate.now(), livro);

		Pagamento pag = new Pagamento(5.0, usuario1);
		

		String res = pagRepo.salva(pag);
		manager.flush();
		manager.clear();

		assertAll(() -> assertEquals("Pagamento realizado com sucesso!", res),
				() -> assertEquals(valorAPagar, pag.getValorPago()));

	}

}
