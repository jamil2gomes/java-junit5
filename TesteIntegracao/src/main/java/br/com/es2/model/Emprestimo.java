package br.com.es2.model;

import static br.com.es2.model.util.DataUtil.dataNaoVencida;
import static br.com.es2.model.util.DataUtil.quantidadeDeDiasAposVencimento;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Emprestimo {

	@Id @GeneratedValue(strategy  = GenerationType.IDENTITY)
	private Long id;
	
	private  LocalDate dataEmprestimo = LocalDate.now();
	private  LocalDate dataPrevista = dataEmprestimo.plusDays(7);
	private  LocalDate dataDevolucao;
	private  boolean isFinalizado = false;
	private  double valorFixo = 5.0;
	
	@ManyToOne (cascade=  CascadeType.ALL)
	@JoinColumn(name = "livro_id")
	private Livro livro;
	
	@ManyToOne(cascade=  CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	public Emprestimo() {}
	
	public Long getId() { return this.id;}
	
	public Usuario getUsuario() {return usuario;}

	public void setUsuario(Usuario usuario) {this.usuario = usuario;}

	public LocalDate getDataDevolucao() {return dataDevolucao;}

	public void setDataDevolucao(LocalDate dataDevolucao) {this.dataDevolucao = dataDevolucao;}

	public Livro getLivro() {return livro;}

	public void setLivro(Livro livro) {
		if(!livro.isReservado()) this.livro = livro;
		else throw new RuntimeException("Não pode realizar emprestimo com livro reservado!");
		}

	public LocalDate getDataEmprestimo() {return dataEmprestimo;}

	public LocalDate getDataPrevista() {return dataPrevista;}
	
	public void setDataPrevista(LocalDate data) { this.dataPrevista = data;}

	public double getValorFixo() {return valorFixo;}
	
	public void setFinalizado(boolean valor) { this.isFinalizado = valor;}
	
	public boolean getIsFinalizado() {return isFinalizado;}

	public double valorTotalAPagar() {

		if (dataNaoVencida(dataPrevista, dataDevolucao)) {

			return this.getValorFixo();

		} else {
			long duracao = quantidadeDeDiasAposVencimento(dataPrevista, dataDevolucao);

			if (duracao <= 8)
				return this.getValorFixo() + (duracao * 0.40);
		}

		return this.getValorFixo() + 3.0;

	}

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
		Emprestimo other = (Emprestimo) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Emprestimo [id=" + id + "]";
	}
	
	
	
	
}
