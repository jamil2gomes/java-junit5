package br.com.es2.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String matricula;
	
	public Usuario() {}
	
	public Long getId() {return id;}

	public String getMatricula() {return matricula;}

	public String getNome() {return nome;}

	public void setNome(String nome) {this.nome = nome;}

	public void setMatricula(String matricula) {this.matricula = matricula;}

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
		Usuario other = (Usuario) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Usuario [nome=" + nome + "]";
	}
	
	

}
