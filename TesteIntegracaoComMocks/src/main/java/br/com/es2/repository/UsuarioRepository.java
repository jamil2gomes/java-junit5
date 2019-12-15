package br.com.es2.repository;

import br.com.es2.model.Usuario;

public interface UsuarioRepository {
	
	void salva(Usuario usuario);
	
	Usuario encontraPor(Long id);
}
