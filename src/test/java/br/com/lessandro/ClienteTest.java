package br.com.lessandro;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lessandro.resources.exception.ValidationException;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClienteTest {
	
//	private static Long clienteId;

//	@Autowired
//	ClienteService clienteService;

	@Test
	public void test01Insert()  {
		try {
//			ClienteDto clienteDto = new ClienteDto("Joaquim Teixeira", "12312312312", "18/12/1980");
//			Cliente cliente = clienteService.insert(clienteDto);
//			clienteId = cliente.getId();
//			assertNotNull(cliente.getId());
		} catch (ValidationException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void test02tUpdate()  {
		try {
//			ClienteDto clienteDto = new ClienteDto("Joaquim Teixeira", "32145689523", "18/12/1980");
//			Cliente cliente = clienteService.atualizarCliente(clienteDto, clienteId.toString());
//			assertEquals(cliente.getCpf(), "32145689523");
		} catch (ValidationException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void test03tDelete()  {
//		clienteService.removePorCpf("32145689523");
//		Cliente cliente = clienteService.buscaPorCpf("32145689523");
//		assertNull(cliente);
	}

}
