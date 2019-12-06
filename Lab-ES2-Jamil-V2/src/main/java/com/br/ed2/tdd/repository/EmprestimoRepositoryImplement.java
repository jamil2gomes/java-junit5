package com.br.ed2.tdd.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.br.ed2.tdd.modelo.Emprestimo;
import com.br.ed2.tdd.modelo.Livro;

public class EmprestimoRepositoryImplement implements EmprestimoRepository {

	private EntityManager manager;

	public EmprestimoRepositoryImplement(EntityManager manager) {
		this.manager = manager;
	}
	
	
	@Override
	public String salva(Emprestimo emprestimo) {
		manager.persist(emprestimo);
		return "Emprestimo salvo com sucesso!";
		
	}

	@Override
	public List<Livro> listaDeLivrosEmAtraso() {
		
		String jpql = "select e.livro from Emprestimo e "
			     + "where e.dataPrevista < now() ";
		
	return manager
			.createQuery(jpql, Livro.class)
			.getResultList();
		
	}

}
