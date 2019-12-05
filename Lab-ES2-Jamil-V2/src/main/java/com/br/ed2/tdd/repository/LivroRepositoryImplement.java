package com.br.ed2.tdd.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.br.ed2.tdd.modelo.Emprestimo;
import com.br.ed2.tdd.modelo.Livro;

public class LivroRepositoryImplement implements LivroRepository {

	private EntityManager manager;

	public LivroRepositoryImplement(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public String salva(Livro livro) {
		manager.persist(livro);
		return "Livro salvo com sucesso!";
	}

	@Override
	public Livro atualiza(Livro livro) {
		return manager.merge(livro);
		
	}
	
	@Override
	public Livro buscaPor(Livro livro) {
		return manager
				.createQuery("from Livro l where l.titulo = :pTitulo", Livro.class)
				.setParameter("pTitulo", livro.getTitulo())
				.getSingleResult();
	}

	@Override
	public List<Emprestimo> historicoDeEmprestimosDo(Livro livro) {
	     TypedQuery<Emprestimo> query =  manager
				.createQuery("select h from Livro l inner join l.historico h  where l.id = :pId", Emprestimo.class)
				.setParameter("pId", livro.getId());
	     
				List<Emprestimo> emprestimos = query.getResultList();
				return emprestimos;
		
	}

	@Override
	public List<Livro> listaLivrosEmprestados() {
		return manager
				.createQuery("from Livro l where l.isEmprestado = true", Livro.class)
				.getResultList();
		
	}

}
