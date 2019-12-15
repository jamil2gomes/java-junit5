package br.com.es2.model;

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
	private Long id;
	private String autor;
	private String titulo;
	private boolean isReservado =  false;
	private boolean isEmprestado = false;
	
	public Livro() {}
	
	@OneToMany(mappedBy = "livro")
	List<Emprestimo> historico = new ArrayList<>();
	
	public Long getId() {return this.id;}
	
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
		return id == other.id;
	}
	
	

}
