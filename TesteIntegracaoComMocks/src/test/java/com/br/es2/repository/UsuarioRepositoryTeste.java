package com.br.es2.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.br.es2.builder.UsuarioBuilder;

import br.com.es2.model.Usuario;
import br.com.es2.repository.UsuarioRepository;
import br.com.es2.repository.UsuarioRepositoryImplementacao;

public class UsuarioRepositoryTeste {

	private EntityManager manager;
	private static EntityManagerFactory emf;

	private Usuario usuario;

	private UsuarioRepository usuarioRepository;

	@BeforeAll
	public static void inicio() {
		emf = Persistence.createEntityManagerFactory("locadoraDev");
	}

	@BeforeEach
	public void antes() {
		manager = emf.createEntityManager();
		manager.getTransaction().begin();

		usuarioRepository = new UsuarioRepositoryImplementacao(manager);

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
	public void deveSalvarUsuario() {
		
		usuarioRepository.salva(usuario);
		manager.flush();
		manager.clear();
		
		Usuario usuarioEncontrado = usuarioRepository.encontraPor(1L);
		
		assertEquals("Jamil", usuarioEncontrado.getNome());
	}

}
