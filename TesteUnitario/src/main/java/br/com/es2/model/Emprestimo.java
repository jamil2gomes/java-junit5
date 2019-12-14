package br.com.es2.model;

import java.time.LocalDate;
import br.com.es2.model.Livro;
import br.com.es2.model.Usuario;
import static br.com.es2.model.util.DataUtil.dataNaoVencida;
import static br.com.es2.model.util.DataUtil.quantidadeDeDiasAposVencimento;

public class Emprestimo {

	private  LocalDate dataEmprestimo = LocalDate.now();
	private  LocalDate dataPrevista = dataEmprestimo.plusDays(7);
	private  LocalDate dataDevolucao;
	private  boolean isFinalizado = false;
	private  double valorFixo = 5.0;
	
	private Livro livro;
	
	private Usuario usuario;
	
	public Usuario getUsuario() {return usuario;}

	public void setUsuario(Usuario usuario) {this.usuario = usuario;}

	public LocalDate getDataDevolucao() {return dataDevolucao;}

	public void setDataDevolucao(LocalDate dataDevolucao) {this.dataDevolucao = dataDevolucao;}

	public Livro getLivro() {return livro;}

	public void setLivro(Livro livro) {this.livro = livro;}

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
}
