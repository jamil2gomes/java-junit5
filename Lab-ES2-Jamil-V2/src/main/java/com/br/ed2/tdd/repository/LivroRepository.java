package com.br.ed2.tdd.repository;

import java.util.List;

import com.br.ed2.tdd.modelo.Emprestimo;
import com.br.ed2.tdd.modelo.Livro;

public interface LivroRepository {

	String salva(Livro livro);
	
	Livro atualiza(Livro livro);
	
	List<Emprestimo> historicoDeEmprestimosDo(Livro livro);
	
	List<Livro>listaLivrosEmprestados();
	
	Livro buscaPor(Livro livro);
	
	
}
