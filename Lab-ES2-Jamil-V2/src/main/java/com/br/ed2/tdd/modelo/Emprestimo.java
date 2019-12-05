package com.br.ed2.tdd.modelo;

import static com.br.ed2.tdd.modelo.util.DataUtil.dataNaoVencida;
import static com.br.ed2.tdd.modelo.util.DataUtil.quantidadeDeDiasAposVencimento;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Emprestimo {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private final LocalDate dataEmprestimo = LocalDate.now();
	private final LocalDate dataPrevista = dataEmprestimo.plusDays(7);
	private LocalDate dataDevolucao;
	private final double valorFixo = 5.0;
	
	@ManyToOne @JoinColumn(name = "livro_id")
	private Livro livro;
	
	@ManyToOne @JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	public Emprestimo() {}
	
	public Integer getId() {return id;}
	
	public Usuario getUsuario() {return usuario;}

	public void setUsuario(Usuario usuario) {this.usuario = usuario;}

	public LocalDate getDataDevolucao() {return dataDevolucao;}

	public void setDataDevolucao(LocalDate dataDevolucao) {this.dataDevolucao = dataDevolucao;}

	public Livro getLivro() {return livro;}

	public void setLivro(Livro livro) {this.livro = livro;}

	public LocalDate getDataEmprestimo() {return dataEmprestimo;}

	public LocalDate getDataPrevista() {return dataPrevista;}

	public double getValorFixo() {return valorFixo;}

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
		return Objects.equals(id, other.id);
	}
	
	

}
