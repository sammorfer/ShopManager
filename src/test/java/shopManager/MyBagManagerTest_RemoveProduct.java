/**
 * 
 */
package shopManager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import exceptions.NoEnoughStock;
import exceptions.NotInStock;
import model.Order;
import model.Product;
import persistency.OrderRepository;
import shopmanager.MyBagManager;
import shopmanager.StockManager;

 
/**
 * @author Samuel Moreno

 *
 */

@ExtendWith(MockitoExtension.class)
class MyBagManagerTest {
	private static Logger trazador=Logger.getLogger(ProductTest.class.getName());
	
	/*Creo los objetos sustitutos (representantes o mocks)
	Son objetos contenidos en MyBagManager de los que a�n no disponemos el c�digo */
	@Mock(serializable = true)
	private static Product productoMock= Mockito.mock(Product.class);
	@Mock
	private static StockManager stockMock= Mockito.mock(StockManager.class);
	@Mock 
	private static OrderRepository repositoryMock= Mockito.mock(OrderRepository.class);
	@Mock
	private static Order orderMock=Mockito.mock(Order.class);

	 
	/*Inyecci�n de dependencias
	Los objetos contenidos en micestaTesteada son reemplazados autom�ticamente por los sustitutos (mocks) */
	@InjectMocks
	private static MyBagManager micestaTesteada;

	
	//Servir�n para conocer el argumento con el que se ha invocado alg�n m�todo de alguno de los mocks (sustitutos o representantes)
	//ArgumentCaptor es un gen�rico, indico al declararlo el tipo del argumento que quiero capturar
	@Captor
	private ArgumentCaptor<Integer> intCaptor;
	@Captor
	private ArgumentCaptor<Product> productCaptor;
	
	/**
	 * @see BeforeEach {@link org.junit.jupiter.api.BeforeEach}
	 */
	
	@BeforeEach
	void setUpBeforeClass(){
		//Todos los tests empiezan con la bolsa vac�a
		
		   micestaTesteada.reset();
	}

	/**
	 * Test method for {@link shopmanager.MyBagManager#removeProduct(model.Product)}.
	 */
	@Test
	final void testRemoveProductProduct() throws NotInStock, NoEnoughStock {
		
		Mockito.when(productoMock.getId()).thenReturn("id");	
		/* Eliminamos un producto que no existe*/		
		try {
			micestaTesteada.removeProduct(productoMock);
	    	/* Debe saltar la excepci�n as� que no debe llegar aqu� */
	    	fail("No salta la excepci�n del stock");
		}
		catch(NotInStock e) {
			/* Capturamos la excepci�n */
			trazador.info("No existe el producto.");
			assertEquals("El producto no existe, no podemos borrarlo",e.getMessage(),"El mensaje de la excepci�n no es correcto");
		}
		try {
	    micestaTesteada.addProduct(productoMock); /* Esta funci�n deber�a funcionar, pero la tratamos por si falla */
		}
		catch(NoEnoughStock e) {
			/* Capturamos la excepci�n */
			trazador.info("No se ha a�adido el producto");
	    	fail("No se ha podido a�adir el producto para despu�s borrarlo");
		}
		/* Hay cosas en la cesta */
		try {
			micestaTesteada.removeProduct(productoMock); 
	    	
		}
		catch(NotInStock e) {
			
			/* No debe saltar la excepci�n as� que no deber�a llegar aqu� */
	    	fail("Salta la excepci�n del stock que no deber�a. ERROR");
			
		}
	   

	    /* Vemos que se ha borrado correctamente*/
	    assertTrue(micestaTesteada.findProduct("id").isEmpty(),"La cesta no est� vac�a");
	    assertFalse(micestaTesteada.findProduct("id").isPresent(),"La cesta no est� vac�a");
	
	}

}