package com.br.es2.builder;

import br.com.es2.model.Usuario;

public class UsuarioBuilder {
	
	private Usuario usuario;

	private UsuarioBuilder() {
	}

	public static UsuarioBuilder umUsuario() {

		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		return builder;
	}

	public UsuarioBuilder comNome(String nome) {
		this.usuario.setNome(nome);
		return this;
	}

	public UsuarioBuilder comMatricula(String matricula) {
		this.usuario.setMatricula(matricula);
		return this;
	}

	public Usuario constroi() {
		return this.usuario;
	}


}
