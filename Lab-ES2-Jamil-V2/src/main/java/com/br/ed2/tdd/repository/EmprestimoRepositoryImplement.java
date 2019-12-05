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
	public void salva(Emprestimo emprestimo) {
		manager.persist(emprestimo);
		
	}

	@Override
	public List<Livro> listaDeLivrosEmAtraso() {
		
		String jpql = "select e.livro from Emprestimo e "
			     + "where e.dataDevolucao > e.dataPrevista ";
		
	return manager
			.createQuery(jpql, Livro.class)
			.getResultList();
		
	}

}
