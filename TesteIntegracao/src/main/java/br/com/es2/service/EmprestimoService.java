package br.com.es2.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import com.br.es2.builder.EmprestimoBuilder;

import br.com.es2.model.Emprestimo;
import br.com.es2.model.Livro;
import br.com.es2.model.Usuario;
import br.com.es2.repository.EmprestimoRepository;

public class EmprestimoService {

	
	private List<Emprestimo> emprestimos = new ArrayList<>();
	private Emprestimo emprestimo;
	private EmprestimoRepository repositorio;
	


	public List<Emprestimo> realizarEmprestimo(Usuario usuario, Livro... livros) {

		this.validacoesPreEmprestimo(usuario, livros);

		for (int i = 0; i < livros.length; i++) {
			emprestimo = EmprestimoBuilder.umEmprestimo().comUsuario(usuario).comLivro(livros[i]).constroi();

			emprestimos.add(emprestimo);
			
			repositorio.salva(emprestimo);
		}

		return emprestimos;
	}
	
	private boolean validacoesPreEmprestimo(Usuario usuario, Livro...livros) {
		
		if (this.buscarEmprestimosDo(usuario) == 2)
			throw new RuntimeException("Usuario não pode ter mais de 3 emprestimos em aberto");

		if (livros.length > 2)
			throw new RuntimeException("Não pode fazer emprestimo com mais de 3 livros!");
		
		return true;
	}

	public int buscarEmprestimosDo(Usuario usuario) {

		int total = 0;
		for (Emprestimo emp : this.emprestimos) {
			if (emp.getUsuario().equals(usuario))
				total++;
		}

		return total;
	}
	

	public void setRepositorio(EmprestimoRepository repo) {
		this.repositorio = repo;
	}
	
	
	public double realizaDevolucao(Emprestimo emprestimo, LocalDate dataDevolucao) {
		emprestimo.setDataDevolucao(dataDevolucao);
		emprestimo.setFinalizado(true);
		emprestimo.getLivro().setEmprestado(false);
		
		repositorio.atualiza(emprestimo);
		
		return emprestimo.valorTotalAPagar();
	}

}
