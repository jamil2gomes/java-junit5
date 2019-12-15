package br.com.es2.repository;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.es2.model.Emprestimo;
import br.com.es2.model.Livro;

public class EmprestimoRepositoryImplementacao implements EmprestimoRepository {

	private EntityManager manager;

	public EmprestimoRepositoryImplementacao(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public void salva(Emprestimo emprestimo) {
		manager.persist(emprestimo);

	}

	@Override
	public List<Livro> livrosEmprestados() {
		return manager.createQuery("from Livro l  where l.isEmprestado = true", Livro.class).getResultList();

	}

	@Override
	public List<Livro> livrosEmAtraso() {

		String jpql = "select e.livro from Emprestimo e where e.dataPrevista < now() ";

		return manager.createQuery(jpql, Livro.class).getResultList();
	}

	@Override
	public Emprestimo buscaEmprestimoPor(Long id) {

		return manager.find(Emprestimo.class, id);
	}

	@Override
	public void atualiza(Emprestimo emprestimo) {
		manager.merge(emprestimo);
		
	}

}
