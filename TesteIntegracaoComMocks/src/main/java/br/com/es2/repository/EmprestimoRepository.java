package br.com.es2.repository;

import java.time.LocalDate;
import java.util.List;

import br.com.es2.model.Emprestimo;
import br.com.es2.model.Livro;

public interface EmprestimoRepository {

	void salva(Emprestimo emprestimo);
	
	void atualiza(Emprestimo emprestimo);
	
	Emprestimo buscaEmprestimoPor(Long id);
	
	List<Livro> livrosEmprestados();
	
	List<Livro>emAtraso();
	
	List<Emprestimo>emprestimosEmAtraso();
	
	List<Emprestimo> estatisticaDeEmprestimoPorPeriodo(LocalDate dataInicio, LocalDate dataFim);
	
	
	
}
