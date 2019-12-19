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
		livro.setEmprestado(true);
		this.emprestimo.setLivro(livro);
		return this;
	}
	
	public EmprestimoBuilder comUsuario(Usuario usuario) {
		this.emprestimo.setUsuario(usuario);
		return this;
	}
		
	public EmprestimoBuilder emAtraso() {
		emprestimo.setDataEmprestimo(LocalDate.now().minusDays(5) );
		emprestimo.setDataPrevista(LocalDate.now().minusDays(6) );
		return this;
	}
	
	
	
	public EmprestimoBuilder comDataLocacao(LocalDate data) {
		this.emprestimo.setDataDevolucao(data);
		return this;
	}
	
	public Emprestimo constroi() {
		return this.emprestimo;
	}

}
