package br.com.es2.service;

import java.util.ArrayList;
import java.util.List;

import br.com.es2.model.Emprestimo;
import br.com.es2.model.Pagamento;
import br.com.es2.model.Usuario;
import br.com.es2.repository.PagamentoRepository;

public class GeradorDePagamentos {

	private PagamentoRepository repository;
	private EmprestimoService servico;
	
	

	public GeradorDePagamentos(PagamentoRepository repository, EmprestimoService servico) {

		this.repository = repository;		
		this.servico = servico;
	}

	
	public List<Pagamento> geraPagamentoDe(Usuario usuario, List<Emprestimo>emprestimos) {

		List<Pagamento> pagamentos = new ArrayList<>();
		Pagamento pagamento = null;
		double valor = 0.0;
		
		for (Emprestimo emprestimo : emprestimos) {
			
			valor += servico.realizaDevolucao(emprestimo, emprestimo.getDataDevolucao());
			
			pagamento = new Pagamento(valor, emprestimo.getUsuario(), emprestimo);
			repository.salva(pagamento);
			pagamentos.add(pagamento);
			valor = 0.0;
		}
		
		return pagamentos;
	}
	
	

}
