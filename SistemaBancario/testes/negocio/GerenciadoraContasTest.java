package negocio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GerenciadoraContasTest {
	
	private final int idConta1 = 1;
	private final int idConta2 = 2;
	
	
	private GerenciadoraContas inicializaGerenciadora(int valorConta1, int valorConta2) {
		List<ContaCorrente> contas = new ArrayList<ContaCorrente>();
		
		contas.add(new ContaCorrente(idConta1, valorConta1, true));
		contas.add(new ContaCorrente(idConta2, valorConta2, true));
		
		return new GerenciadoraContas(contas);
	}
	
	@Test 
	public void testSaldoSuficiente() {
		GerenciadoraContas gerenciadora = inicializaGerenciadora(200, 0);
		
		boolean transferenciaComSucesso = gerenciadora.transfereValor(idConta1, 100, idConta2);
		
		assertTrue(transferenciaComSucesso);
		assertThat(gerenciadora.getContasDoBanco().get(0).getSaldo(), is(100.0));
		assertThat(gerenciadora.getContasDoBanco().get(1).getSaldo(), is(100.0));
	}
	
	@Test
	public void testSaldoInsuficienteEPositivo() {
		GerenciadoraContas gerenciadora = inicializaGerenciadora(100, 0);
		
		boolean transferenciaComSucesso = gerenciadora.transfereValor(idConta1, 200, idConta2);
		
		assertTrue(transferenciaComSucesso);
		assertThat(gerenciadora.getContasDoBanco().get(0).getSaldo(), is(-100.0));
		assertThat(gerenciadora.getContasDoBanco().get(1).getSaldo(), is(200.0));
	}
	
	@Test
	public void testSaldoInsuficienteENegativo() {
		GerenciadoraContas gerenciadora = inicializaGerenciadora(-100, 0);
		
		boolean transferenciaComSucesso = gerenciadora.transfereValor(idConta1, 200, idConta2);
		
		assertTrue(transferenciaComSucesso);
		assertThat(gerenciadora.getContasDoBanco().get(0).getSaldo(), is(-300.0));
		assertThat(gerenciadora.getContasDoBanco().get(1).getSaldo(), is(200.0));
		
	}
	
	@Test
	public void testSaldoInsuficiente() {
		GerenciadoraContas gerenciadora = inicializaGerenciadora(-100, -100);
		
		boolean transferenciaComSucesso = gerenciadora.transfereValor(idConta1, 200, idConta2);
		
		assertTrue(transferenciaComSucesso);
		assertThat(gerenciadora.getContasDoBanco().get(0).getSaldo(), is(-300.0));
		assertThat(gerenciadora.getContasDoBanco().get(1).getSaldo(), is(100.0));
		
	}
}
