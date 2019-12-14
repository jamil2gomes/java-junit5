package br.com.es2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.es2.model.Emprestimo;

public class Livro {
	
	private String autor;
	private String titulo;
	private boolean isReservado =  false;
	private boolean isEmprestado = false;
	
	List<Emprestimo> historico = new ArrayList<>();
	
    public String getAutor() {return autor;}
	
	public void setAutor(String autor) {this.autor = autor;}
	
	public String getTitulo() {return titulo;}
	
	public void setTitulo(String titulo) {this.titulo = titulo;}
	
	public boolean isReservado() {return isReservado;}
	
	public void setReservado(boolean isReservado) {this.isReservado = isReservado;}
	
	public boolean isEmprestado() {return isEmprestado;}
	
	public void setEmprestado(boolean isEmprestado) {this.isEmprestado = isEmprestado;}
	
	public List<Emprestimo> getHistorico() {return Collections.unmodifiableList(historico);}
	
	public void adiciona(Emprestimo emprestimo) {this.historico.add(emprestimo);}

}
