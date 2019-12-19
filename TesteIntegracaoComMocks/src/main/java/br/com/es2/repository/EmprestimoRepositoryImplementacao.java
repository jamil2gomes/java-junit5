package br.com.es2.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.es2.model.Emprestimo;
import br.com.es2.model.Livro;
import br.com.es2.model.Usuario;

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
	public List<Livro> emAtraso() {

		String jpql = "select e.livro from Emprestimo e where e.dataDevolucao is null and e.dataPrevista < :pHoje ";

		return manager.createQuery(jpql, Livro.class)
				.setParameter("pHoje", LocalDate.now())
				.getResultList();
	}

	@Override
	public Emprestimo buscaEmprestimoPor(Long id) {

		return manager.find(Emprestimo.class, id);
	}

	@Override
	public void atualiza(Emprestimo emprestimo) {
		manager.merge(emprestimo);
		
	}

	@Override
	public List<Emprestimo> emprestimosEmAtraso() {
		
	 String jpql = "from Emprestimo e where e.dataDevolucao is null and e.dataPrevista < :pHoje ";

		return manager.createQuery(jpql, Emprestimo.class)
				.setParameter("pHoje", LocalDate.now().plusDays(10))
				.getResultList();
	}

	@Override
	public List<Emprestimo> estatisticaDeEmprestimoPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
		
		String jpql = "from Emprestimo e "
			        + "where e.dataEmprestimo between :pDataInicio and :pDataFim";
	
	return manager
			.createQuery(jpql, Emprestimo.class)
			.setParameter("pDataInicio", dataInicio )
			.setParameter("pDataFim", dataFim )
			.getResultList();
	}

	@Override
	public List<Emprestimo> emprestimosEmAtraso(Usuario usuario) {
		
		 String jpql = "from Emprestimo e where e.dataDevolucao is null and e.dataPrevista < :pHoje"
		 		+ "  and e.usuario = pUsuario";

			return manager.createQuery(jpql, Emprestimo.class)
					.setParameter("pHoje", LocalDate.now().plusDays(10))
					.setParameter("pUsuario", usuario.getId())
					.getResultList();
	}

}
