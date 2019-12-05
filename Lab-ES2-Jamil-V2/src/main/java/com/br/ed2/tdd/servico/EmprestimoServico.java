package com.br.ed2.tdd.servico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.br.ed2.tdd.modelo.Emprestimo;
import com.br.ed2.tdd.modelo.Livro;
import com.br.ed2.tdd.modelo.Usuario;

public class EmprestimoServico {

	private Emprestimo emprestimo;
	private List<Usuario> emprestimoUsuarios = new ArrayList<>();

	public EmprestimoServico() {this.emprestimo = new Emprestimo();}

	public boolean emprestar(Usuario usuario, Livro... livros) {
		
        emprestimoUsuarios.add(usuario);
		
		if (this.consultarEmprestimosPorUsuario(usuario) == 3)
			 throw new RuntimeException("Usuario não pode ter 3 empréstimos");

		if (livros.length > 2)
			throw new RuntimeException("Usuario não pode ter 3 livros emprestados ao mesmo tempo");

		for (Livro livro : livros) {
			if (livro.isReservado())
				throw new RuntimeException("Não pode emprestar livro reservado");
		}
		
		emprestimo.setUsuario(usuario);
		
		
		for (int i = 0; i < livros.length; i++) {

			livros[i].setEmprestado(true);
			emprestimo.setLivro(livros[i]);
			livros[i].adiciona(emprestimo);
		}
		
		return true;
	}

	public Emprestimo getEmprestimo() {
		return emprestimo;
	}

	public int consultarEmprestimosPorUsuario(Usuario usuario) {

		int total = 0;

		for (Usuario usuarioComEmprestimo : emprestimoUsuarios) {
			if (usuarioComEmprestimo.equals(usuario))
				++total;
		}
		return total;
	}

	public double devolver(LocalDate dataDevolucao, Livro... livros) {

		emprestimo.setDataDevolucao(dataDevolucao);

		for (Livro livro : livros) {
			livro.setEmprestado(false);
		}

		return emprestimo.valorTotalAPagar();

	}

}
