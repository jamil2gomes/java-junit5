package com.br.ed2.tdd.repository;

import javax.persistence.EntityManager;

import com.br.ed2.tdd.modelo.Usuario;

public class UsuarioRepositoryImplement implements UsuarioRepository {

	private EntityManager manager;

	public UsuarioRepositoryImplement(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void salvar(Usuario usuario) {
	 manager.persist(usuario);		
	}

}
