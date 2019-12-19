package com.br.es2.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.br.es2.builder.EmprestimoBuilder;
import com.br.es2.builder.LivroBuilder;
import com.br.es2.builder.UsuarioBuilder;

import br.com.es2.model.Emprestimo;
import br.com.es2.model.Livro;
import br.com.es2.model.Pagamento;
import br.com.es2.model.Usuario;
import br.com.es2.repository.EmprestimoRepository;
import br.com.es2.repository.PagamentoRepository;
import br.com.es2.service.EmprestimoService;
import br.com.es2.service.GeradorDePagamentos;

public class GeradorPagamentoTeste {

	private EmprestimoRepository repositorio;
	private PagamentoRepository repositorioPag;
	private EmprestimoService servico;
	private GeradorDePagamentos gerador;
	private Livro livro,livro2;

	
	@BeforeEach
	public void setUp() {
		
		repositorio = Mockito.mock(EmprestimoRepository.class);
		repositorioPag = Mockito.mock(PagamentoRepository.class);
		servico = Mockito.mock(EmprestimoService.class);
		livro = LivroBuilder.umLivro().comAutor("Autor 1").comTitulo("Livro 1").ehReservado(false).constroi();
		livro2 = LivroBuilder.umLivro().comAutor("Autor X").comTitulo("Titulo Y").constroi();
		
		gerador = new GeradorDePagamentos(repositorioPag, servico);
		
		
	}
	
	@Test
	public void deveRealizarPagamentoDeEmprestimosAtrasados() {
		Usuario usuario1 = UsuarioBuilder.umUsuario().comNome("User1").constroi();

			
		List<Emprestimo> emprestimosDevolvidosComAtraso = Arrays.asList(
				EmprestimoBuilder.umEmprestimo().comUsuario(usuario1).comLivro(livro).emAtraso().comDataLocacao(LocalDate.now()).constroi(),
				EmprestimoBuilder.umEmprestimo().comUsuario(usuario1).comLivro(livro2).emAtraso().comDataLocacao(LocalDate.now().plusDays(3)).constroi() );
		
			
		Mockito.when(repositorio.emprestimosEmAtraso()).thenReturn(emprestimosDevolvidosComAtraso);
		
		Mockito.when(servico.realizaDevolucao(emprestimosDevolvidosComAtraso.get(0), LocalDate.now())).thenReturn(7.8);
		Mockito.when(servico.realizaDevolucao(emprestimosDevolvidosComAtraso.get(1), LocalDate.now().plusDays(3))).thenReturn(8.0);
		
		
		List<Pagamento> pagamentos = gerador.geraPagamentoDe(usuario1, emprestimosDevolvidosComAtraso);
		
	

		Mockito.verify(repositorioPag, Mockito.times(2)).salva(pagamentos.get(0));
		Mockito.verify(repositorioPag, Mockito.times(2)).salva(pagamentos.get(1));
		
		
		
		
	}
}
