package br.com.es2.repository;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.es2.model.Emprestimo;
import br.com.es2.model.Livro;

public class LivroRepositoryImplementacao implements LivroRepository {

	private EntityManager manager;
	
	public LivroRepositoryImplementacao(EntityManager manager) {
		this.manager = manager;
	}
	
	
	@Override
	public void salva(Livro livro) {
		manager.persist(livro);
		
	}

	@Override
	public Livro atualiza(Livro livro) {
		return manager.merge(livro);
		
	}

	@Override
	public List<Emprestimo> historicoDeEmprestimosDo(Livro livro) {
		
		String jpql = "select h from Livro l inner join l.historico h  where l.id = :pId";
		
		return manager.createQuery(jpql, Emprestimo.class).
				setParameter("pId", livro.getId()).
				getResultList();
	}


	@Override
	public Livro buscaPor(Long id) {
		return manager.find(Livro.class, id);
	}

}
