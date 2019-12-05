package com.br.ed2.tdd.modelo;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String matricula;

	public Usuario() {}

	
	public Integer getId() {return id;}
	
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
		return Objects.equals(id, other.id);
	}
	
	
}
