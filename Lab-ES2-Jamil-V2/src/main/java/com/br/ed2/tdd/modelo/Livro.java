package com.br.ed2.tdd.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Livro {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String autor;
	private String titulo;
	private boolean isReservado =  false;
	private boolean isEmprestado = false;
	
	@OneToMany(mappedBy = "livro")
	List<Emprestimo> historico = new ArrayList<>();
	
	public Livro() {}
	
	public Livro(String titulo) {this.titulo = titulo;}

	public Integer getId() {return id;}

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

	@Override
	public int hashCode() {return Objects.hash(id);}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro other = (Livro) obj;
		return Objects.equals(id, other.id);
	}
}
