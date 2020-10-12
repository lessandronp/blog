package br.com.lessandro;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lessandro.repository.AlbumRepository;
import br.com.lessandro.resources.exception.ValidationException;

@RunWith(SpringRunner.class)
public class ClienteMockTest {

//	@InjectMocks
//	private ClienteService clienteService;

	@Mock
	private AlbumRepository clienteRepository;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void insereCliente() {
		try {
//			ClienteDto clienteDto = new ClienteDto("Marco Pereira", "12345689563", "10/02/1975");
//			Cliente cliente = clienteService.preparaNovoCliente(clienteDto);
//			when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
//			assertNotNull(cliente.getCpf());
		} catch (ValidationException e) {
			Assert.fail();
		}
	}

}
