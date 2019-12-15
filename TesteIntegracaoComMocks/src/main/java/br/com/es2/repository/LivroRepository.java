package br.com.es2.repository;

import java.util.List;

import br.com.es2.model.Emprestimo;
import br.com.es2.model.Livro;

public interface LivroRepository {

	void salva(Livro livro);
	Livro atualiza(Livro livro);
	Livro buscaPor(Long id);
	List<Emprestimo>historicoDeEmprestimosDo(Livro livro);
	
}
