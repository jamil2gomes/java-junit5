package com.br.ed2.tdd.modelo;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Pagamento {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Double valorPago;
	
	@ManyToOne(cascade = CascadeType.PERSIST) @JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@OneToOne(cascade = CascadeType.PERSIST) @JoinColumn(name = "emprestimo_id")
	private Emprestimo emprestimo;
	
	public Pagamento() {}
	
	public Pagamento(Double valorPago, Usuario usuario, Emprestimo emprestimo) {
		this.valorPago = valorPago;
		this.usuario = usuario;
		this.emprestimo = emprestimo;
	}
	
	public Integer getId() {return id;}

	public Double getValorPago() {return valorPago;}

	public Usuario getUsuario() {return usuario;}

	public Emprestimo getEmprestimo() {return emprestimo;}

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
		Pagamento other = (Pagamento) obj;
		return Objects.equals(id, other.id);
	}
}
