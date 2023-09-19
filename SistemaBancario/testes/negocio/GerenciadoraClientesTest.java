package negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GerenciadoraClientesTest {

	private GerenciadoraClientes gerenciadora;
	private final int idClient1 = 1;
	private final int idClient2 = 2;
	
	@Before
	public void inicializa() {
		Cliente cliente = new Cliente(idClient1, "Ronald", 20, "ronald.garcia@senacsp.edu.br", idClient1, true);
		Cliente cliente2 = new Cliente(idClient2, "Agatha", 20, "agatha.cristina@senacsp.edu.br", idClient2, true);
		
		
		List<Cliente> clientes = new ArrayList();
		
		clientes.add(cliente);
		clientes.add(cliente2);
		
		gerenciadora = new GerenciadoraClientes(clientes);
	}
	
	
	@After
	public void down() {
		gerenciadora.limpa();
	}
	
	@Test
	public void testPesquisaCliente() {
		
		Cliente cliente1 = gerenciadora.pesquisaCliente(idClient1);
		
		assertThat(cliente1.getId(), is(idClient1));
	}
	
	@Test
	public void testIdadeValidaCliente() throws IdadeNaoPermitidaException {
		Cliente cliente1 = gerenciadora.pesquisaCliente(idClient1);
		cliente1.setIdade(65);
		
		int idade = cliente1.getIdade();
		
		boolean idadevalida = gerenciadora.validaIdade(idade);
		
		assertTrue(idadevalida);
	}
	
	@Test
	public void testaIdadeValidaCliente2() throws IdadeNaoPermitidaException {
		Cliente cliente1 = gerenciadora.pesquisaCliente(idClient1);
		cliente1.setIdade(18);
		
		int idade = cliente1.getIdade();
		
		boolean idadevalida = gerenciadora.validaIdade(idade);
		
		assertTrue(idadevalida);
	}
	
	@Test
	public void testaIdadeInvalidaCliente() {
		Cliente cliente1 = gerenciadora.pesquisaCliente(idClient1);
		cliente1.setIdade(17);
		
		int idade = cliente1.getIdade();
		boolean idadevalida = false;
		
		try {
			idadevalida = gerenciadora.validaIdade(idade);	
		} catch (IdadeNaoPermitidaException e) {
			assertFalse(idadevalida);		
		}
	}
	
	@Test
	public void testaIdadeInvalidaCliente2(){
		Cliente cliente1 = gerenciadora.pesquisaCliente(idClient1);
		cliente1.setIdade(66);
		
		boolean idadevalida = false;
		int idade = cliente1.getIdade();
		
		try {
			idadevalida = gerenciadora.validaIdade(idade);	
		} catch (IdadeNaoPermitidaException e) {
			assertFalse(idadevalida);		
		}
	}
	
	
	@Test
	public void testRemoveCliente() {
		
		boolean removido = gerenciadora.removeCliente(idClient2);
		
		assertTrue(removido);
		assertEquals(gerenciadora.getClientesDoBanco().size(), 1);
		assertFalse(gerenciadora.clienteAtivo(idClient2));
	}
	
}
