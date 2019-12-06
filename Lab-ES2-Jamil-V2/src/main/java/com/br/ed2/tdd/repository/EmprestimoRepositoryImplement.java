package com.br.ed2.tdd.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.br.ed2.tdd.modelo.Emprestimo;
import com.br.ed2.tdd.modelo.Livro;
import com.br.ed2.tdd.modelo.Usuario;

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
	
	public List<Emprestimo> consultarEmprestimoPor(Usuario usuario){
		String jpql = "from Emprestimo e "
				+ "where e.usuario.id = :pUsuario and"
				+ " e.isFinalizado = false";
		
		return manager
				.createQuery(jpql, Emprestimo.class)
				.setParameter("pUsuario", usuario.getId())
				.getResultList();
	}
	
	public Emprestimo buscaEmprestimoPor(String nomeUser, String nomeLivro){
		String jpql = "from Emprestimo e "
				+ "where e.usuario.nome = :pUsuario and"
				+ " e.livro.titulo = :pLivro";
		
		return manager
				.createQuery(jpql, Emprestimo.class)
				.setParameter("pUsuario", nomeUser)
				.setParameter("pLivro", nomeLivro)
				.getSingleResult();
	}


	@Override
	public Emprestimo atualiza(Emprestimo emprestimo) {
		return manager.merge(emprestimo);
		
	}
	
	

}
