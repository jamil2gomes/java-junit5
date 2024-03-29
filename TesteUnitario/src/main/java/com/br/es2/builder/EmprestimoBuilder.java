package com.br.es2.builder;

import java.time.LocalDate;

import br.com.es2.model.Emprestimo;
import br.com.es2.model.Livro;
import br.com.es2.model.Usuario;

public class EmprestimoBuilder {
	
	private Emprestimo emprestimo;
	
	private EmprestimoBuilder() {}
	
	public static EmprestimoBuilder umEmprestimo() {
		EmprestimoBuilder builder = new EmprestimoBuilder();
		
		builder.emprestimo = new Emprestimo();
		return builder;
	}
	
	public EmprestimoBuilder comLivro(Livro livro) {
		this.emprestimo.setLivro(livro);
		return this;
	}
	
	public EmprestimoBuilder comUsuario(Usuario usuario) {
		this.emprestimo.setUsuario(usuario);
		return this;
	}
	
	public EmprestimoBuilder comDataDevolucao(LocalDate dataDevolucao) {
		this.emprestimo.setDataDevolucao(dataDevolucao);
		return this;
	}
	
	public Emprestimo constroi() {
		return this.emprestimo;
	}

}
