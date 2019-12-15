package br.com.es2.repository;

import br.com.es2.model.Pagamento;

public interface PagamentoRepository {

	void salva(Pagamento pagamento);
	Pagamento buscaPor(long id);
}
