package com.br.ed2.tdd.repository;

import java.util.List;

import com.br.ed2.tdd.modelo.Emprestimo;
import com.br.ed2.tdd.modelo.Livro;
import com.br.ed2.tdd.modelo.Usuario;

public interface EmprestimoRepository {

	String salva(Emprestimo emprestimo);
	
	List<Livro> listaDeLivrosEmAtraso();
	
	public List<Emprestimo> consultarEmprestimoPor(Usuario usuario);

	Emprestimo atualiza(Emprestimo emprestimo);
	
	Emprestimo buscaEmprestimoPor(String nomeUser, String nomeLivro);

	
}
