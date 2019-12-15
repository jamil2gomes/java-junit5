package br.com.es2.repository;

import javax.persistence.EntityManager;

import br.com.es2.model.Usuario;

public class UsuarioRepositoryImplementacao implements UsuarioRepository {

	private EntityManager manager;

	public UsuarioRepositoryImplementacao(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public void salva(Usuario usuario) {

		manager.persist(usuario);
	}

	@Override
	public Usuario encontraPor(Long id) {
		
		return manager.find(Usuario.class, id);
	}

}
