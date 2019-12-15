package br.com.es2.repository;

import javax.persistence.EntityManager;

import br.com.es2.model.Pagamento;

public class PagamentoRepositoryImplementacao implements PagamentoRepository {

	private EntityManager manager;

	public PagamentoRepositoryImplementacao(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public void salva(Pagamento pagamento) {
		manager.persist(pagamento);

	}

	@Override
	public Pagamento buscaPor(long id) {

		return manager.find(Pagamento.class, id);
	}

}
