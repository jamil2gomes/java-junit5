package com.br.ed2.tdd.modelo;

import java.time.LocalDate;

import static com.br.ed2.tdd.util.DataUtil.dataNaoVencida;
import static com.br.ed2.tdd.util.DataUtil.quantidadeDeDiasAposDataPrevista;

public class Emprestimo {

	private Usuario usuario;
	private final LocalDate dataEmprestimo = LocalDate.now();
	private final LocalDate dataPrevista = dataEmprestimo.plusDays(7);
	private LocalDate dataDevolucao;
	private Livro livro;
	private final double valorFixo = 5.0;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public LocalDate getDataEmprestimo() {
		return dataEmprestimo;
	}

	public LocalDate getDataPrevista() {
		return dataPrevista;
	}

	public double getValorFixo() {
		return valorFixo;
	}

	public double valorTotalAPagar() {

		if (dataNaoVencida(this.dataDevolucao, this.dataPrevista)) {

			return this.getValorFixo();

		} else {
			long duracao = quantidadeDeDiasAposDataPrevista(this.dataDevolucao, this.dataPrevista);

			if (duracao <= 8)
				return this.getValorFixo() + (duracao * 0.40);
		}

		return this.getValorFixo() + 3.0;

	}

}
