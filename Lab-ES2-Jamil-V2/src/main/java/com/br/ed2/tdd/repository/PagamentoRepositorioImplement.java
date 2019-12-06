package com.br.ed2.tdd.repository;

import javax.persistence.EntityManager;

import com.br.ed2.tdd.modelo.Pagamento;

public class PagamentoRepositorioImplement implements PagamentoRepository{
	
	private EntityManager manager;

	public PagamentoRepositorioImplement(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public String salva(Pagamento pagamento) {
		manager.persist(pagamento);
		return "Pagamento realizado com sucesso!";
		
	}
	
	

}
