package com.br.ed2.tdd.servico;

import java.time.LocalDate;

import com.br.ed2.tdd.modelo.Emprestimo;
import com.br.ed2.tdd.modelo.Livro;
import com.br.ed2.tdd.modelo.Usuario;
import com.br.ed2.tdd.repository.EmprestimoRepository;

public class EmprestimoServico {

	private Emprestimo emprestimo;
	private EmprestimoRepository emprestimoRepo;

	public EmprestimoServico(EmprestimoRepository emprestimoRepo) {this.emprestimoRepo = emprestimoRepo;}

	public boolean emprestar(Usuario usuario, Livro... livros) {
		
		if (this.consultarEmprestimosPorUsuario(usuario) >= 3)
			 throw new RuntimeException("Usuario n�o pode ter 3 empr�stimos");

		if (livros.length > 2)
			throw new RuntimeException("Usuario n�o pode ter 3 livros emprestados ao mesmo tempo");

		for (Livro livro : livros) {
			if (livro.isReservado())
				throw new RuntimeException("N�o pode emprestar livro reservado");
		}
		
		for (Livro livro: livros) {
            emprestimo = new Emprestimo();
            emprestimo.setUsuario(usuario);
			livro.setEmprestado(true);
			emprestimo.setLivro(livro);
			emprestimoRepo.salva(emprestimo);
		}
		
		return true;
	}

	
	public int consultarEmprestimosPorUsuario(Usuario usuario) {

		int total = 0;

		for (Emprestimo emprestimos : emprestimoRepo.consultarEmprestimoPor(usuario)) {
			if (emprestimos.getUsuario().equals(usuario))
				total++;
		}
		return total;
	}

	public double devolver(Usuario usuario, LocalDate dataDevolucao, Livro... livros) {
		
        
         double total = 0.0;
         
         for (Livro livro : livros) {
 			livro.setEmprestado(false);
 		}
         
		 for(Emprestimo emprestimoBuscado: emprestimoRepo.consultarEmprestimoPor(usuario)) {
			 
			 emprestimoBuscado.setDataDevolucao(dataDevolucao);
			 emprestimoBuscado.setFinalizado(true);
			 emprestimoRepo.atualiza(emprestimoBuscado);
			 
			 total += emprestimoBuscado.valorTotalAPagar();
		 }
		 
		 return total;

	}

}
