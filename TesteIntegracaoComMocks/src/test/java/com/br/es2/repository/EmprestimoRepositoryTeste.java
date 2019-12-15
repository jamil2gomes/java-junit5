package com.br.es2.repository;

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

import com.br.es2.builder.LivroBuilder;
import com.br.es2.builder.UsuarioBuilder;

import br.com.es2.model.Emprestimo;
import br.com.es2.model.Livro;
import br.com.es2.model.Usuario;
import br.com.es2.repository.EmprestimoRepository;
import br.com.es2.repository.EmprestimoRepositoryImplementacao;
import br.com.es2.service.EmprestimoService;

public class EmprestimoRepositoryTeste {
	
	private EntityManager manager;
	private static EntityManagerFactory emf;
	
	private Livro livro, livro2;
	private Usuario usuario;
	private EmprestimoRepository emprestimoRepo;

	@BeforeAll
	public static void inicio() {
		emf = Persistence.createEntityManagerFactory("locadoraDev");
	}

	@BeforeEach
	public void antes() {
		manager = emf.createEntityManager();
		manager.getTransaction().begin();
		emprestimoRepo = new EmprestimoRepositoryImplementacao(manager);
		livro = LivroBuilder.umLivro().comAutor("Autor X").comTitulo("Titulo Y").constroi();
		livro2 = LivroBuilder.umLivro().comAutor("Autor X").comTitulo("Titulo Y").constroi();
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
	public void deveSalvarEmprestimo() {
		
		EmprestimoService  servico = new EmprestimoService();
		
		List<Emprestimo>emprestimosRealizados = servico.realizarEmprestimo(usuario, livro);
		
		for(Emprestimo emprestimo: emprestimosRealizados)
				emprestimoRepo.salva(emprestimo);
		
		
		Emprestimo emprestimoBuscado = emprestimoRepo.buscaEmprestimoPor(4L);
		
		assertEquals("Jamil", emprestimoBuscado.getUsuario().getNome());
		assertEquals("Titulo Y", emprestimoBuscado.getLivro().getTitulo());
	}
	
	@Test
	public void deveBuscarLivrosEmprestados() {
		
		EmprestimoService  servico = new EmprestimoService();
		
		List<Emprestimo>emprestimosRealizados = servico.realizarEmprestimo(usuario, livro, livro2);
		
		for(Emprestimo emprestimo: emprestimosRealizados)
				emprestimoRepo.salva(emprestimo);
		
		
		List<Livro> livrosEmprestados = emprestimoRepo.livrosEmprestados();
		
		assertEquals(2, livrosEmprestados.size());
		
	}
	
	@Test
	public void deveBuscarLivrosAtrasados() {
		
		EmprestimoService  servico = new EmprestimoService();
		
		List<Emprestimo>emprestimosRealizados = servico.realizarEmprestimo(usuario, livro);
		
		for(Emprestimo emprestimo: emprestimosRealizados) {
			emprestimo.setDataPrevista(LocalDate.now().minusDays(1));
			emprestimoRepo.salva(emprestimo);
		}
			
				
		
		
		List<Livro> livrosEmprestados = emprestimoRepo.livrosEmAtraso();
		
		assertEquals(1, livrosEmprestados.size());
		
	}

}
