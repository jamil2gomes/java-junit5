package br.com.es2.repository;

import java.util.List;

import br.com.es2.model.Emprestimo;
import br.com.es2.model.Livro;

public interface EmprestimoRepository {

	void salva(Emprestimo emprestimo);
	
	void atualiza(Emprestimo emprestimo);
	
	List<Livro> livrosEmprestados();
	
	List<Livro>livrosEmAtraso();
	
	Emprestimo buscaEmprestimoPor(Long id);
	
}
